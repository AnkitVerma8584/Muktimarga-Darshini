package com.ass.madhwavahini.ui.presentation.navigation.screens.document.pdf

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.data.remote.Api.getAudioUrl
import com.ass.madhwavahini.domain.modals.Track
import com.ass.madhwavahini.domain.repository.DocumentRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.domain.wrapper.UiState
import com.ass.madhwavahini.util.player.MyPlayer
import com.ass.madhwavahini.util.player.PlaybackState
import com.ass.madhwavahini.util.player.PlayerEvents
import com.ass.madhwavahini.util.player.PlayerStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PdfViewModel @Inject constructor(
    private val filesRepository: DocumentRepository,
    private val savedStateHandle: SavedStateHandle,
    private val myPlayer: MyPlayer
) : ViewModel(), PlayerEvents {

    private val _pdfState = MutableStateFlow(UiState<File>())
    val pdfState = _pdfState.asStateFlow()

    /////////////////////////////////  AUDIO  /////////////////////////////////
    var currentTrack: Track by mutableStateOf(getTrackInfo())
        private set

    var hasAudioFile: Boolean by mutableStateOf(false)
        private set

    private var isTrackPlay: Boolean = false

    private var playbackStateJob: Job? = null

    private val _playbackState = MutableStateFlow(PlaybackState(0L, 0L))
    val playbackState: StateFlow<PlaybackState> get() = _playbackState.asStateFlow()

    ////////////////////////////////  AUDIO  /////////////////////////////////
    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchFile(
                savedStateHandle.get<Int>("file_id") ?: 0,
                savedStateHandle.get<String>("file_url") ?: ""
            )
        }
        if (currentTrack.trackUrl.isNotBlank()) {
            hasAudioFile = true
            myPlayer.initPlayer(currentTrack.trackUrl)
            observePlayerState()
        }
    }


    private suspend fun fetchFile(homeFileId: Int, homeFileUrl: String) {
        filesRepository.getDocument("file_${homeFileId}.pdf", homeFileUrl)
            .collectLatest { result ->
                _pdfState.value = result
            }
    }

    fun setPdfError(throwable: Throwable) {
        _pdfState.update {
            it.copy(
                isLoading = false,
                error = StringUtil.DynamicText(throwable.message ?: "Unable to render pdf"),
                data = null
            )
        }
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**                                               AUDIO                                                */
/////////////////////////////////////////////////////////////////////////////////////////////////////////


    private fun getTrackInfo(): Track {
        val audioUrl = savedStateHandle.get<String>("audio_url").getAudioUrl()
        val audioImage = savedStateHandle.get<String>("audio_image").getAudioUrl()
        return if (audioUrl.isBlank()) Track() else
            Track(
                trackUrl = audioUrl,
                trackImage = audioImage
            )
    }

    private fun updateState(state: PlayerStates) {
        isTrackPlay = (state == PlayerStates.STATE_PLAYING)
        currentTrack = currentTrack.copy(state = state)
        updatePlaybackState(state)
    }

    private fun observePlayerState() {
        viewModelScope.launch {
            myPlayer.playerState.collect {
                updateState(it)
            }
        }
    }


    private fun updatePlaybackState(state: PlayerStates) {
        playbackStateJob?.cancel()
        playbackStateJob = viewModelScope.launch {
            do {
                _playbackState.emit(
                    PlaybackState(
                        currentPlaybackPosition = myPlayer.currentPlaybackPosition,
                        currentTrackDuration = myPlayer.currentTrackDuration
                    )
                )
                delay(1000)
            } while (state == PlayerStates.STATE_PLAYING && isActive)
        }
    }

    override fun onSeekForward() {
        myPlayer.seekForward()
    }

    override fun onSeekBackward() {
        myPlayer.seekBackward()
    }

    override fun onPlayPauseClick() {
        myPlayer.playPause()
    }

    override fun onSeekBarPositionChanged(position: Long) {
        myPlayer.seekToPosition(position)
    }

    override fun onCleared() {
        super.onCleared()
        myPlayer.releasePlayer()
    }
}