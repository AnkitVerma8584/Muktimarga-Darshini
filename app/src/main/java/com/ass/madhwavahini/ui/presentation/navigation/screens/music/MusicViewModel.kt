package com.ass.madhwavahini.ui.presentation.navigation.screens.music

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.data.remote.Api
import com.ass.madhwavahini.data.remote.Api.getDocumentExtension
import com.ass.madhwavahini.domain.modals.Track
import com.ass.madhwavahini.domain.player.MyPlayer
import com.ass.madhwavahini.domain.player.PlaybackState
import com.ass.madhwavahini.domain.player.PlayerEvents
import com.ass.madhwavahini.domain.player.PlayerStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val myPlayer: MyPlayer,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), PlayerEvents {

    var currentTrack: Track by mutableStateOf(getTrackInfo())
        private set

    private var isTrackPlay: Boolean = false

    private var playbackStateJob: Job? = null

    private val _playbackState = MutableStateFlow(PlaybackState(0L, 0L))
    val playbackState: StateFlow<PlaybackState> get() = _playbackState.asStateFlow()


    private fun getTrackInfo(): Track {
        val fileId = savedStateHandle.get<Int>("file_id") ?: 0
        val fileName = savedStateHandle.get<String>("file_name") ?: ""
        val fileAuthor = savedStateHandle.get<String>("file_author") ?: ""
        val fileUrl = savedStateHandle.get<String>("file_url") ?: ""
        return Track(
            trackId = fileId,
            trackName = fileName,
            trackUrl = Api.BASE_URL + fileUrl.getDocumentExtension(),
            artistName = fileAuthor,
        )
    }

    init {
        myPlayer.initPlayer(currentTrack.trackUrl)
        observePlayerState()
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
                delay(1000) // delay for 1 second
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
        viewModelScope.launch {
            myPlayer.seekToPosition(position)
        }
    }

    override fun onCleared() {
        super.onCleared()
        myPlayer.releasePlayer()
    }
}