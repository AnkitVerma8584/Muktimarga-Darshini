package com.ass.madhwavahini.ui.presentation.navigation.screens.music

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val currentTrack: Track get() = getTrackInfo()

    private var isTrackPlay: Boolean = false

    private var playbackStateJob: Job? = null

    private val _playbackState = MutableStateFlow(PlaybackState(0L, 0L))
    val playbackState: StateFlow<PlaybackState> get() = _playbackState.asStateFlow()

    private var isAuto: Boolean = false

    private fun getTrackInfo(): Track {
        val fileId = savedStateHandle.get<Int>("file_id") ?: 0
        val fileName = savedStateHandle.get<String>("file_name") ?: ""
        val fileAuthor = savedStateHandle.get<String>("file_author") ?: ""
        val fileUrl = savedStateHandle.get<String>("file_url") ?: ""
        return Track(
            trackId = fileId,
            trackName = fileName,
            trackUrl = fileUrl,
            artistName = fileAuthor,
        )
    }

    init {
        myPlayer.initPlayer(currentTrack.trackUrl)
        observePlayerState()
    }


    private fun setUpTrack() {
        if (!isAuto) myPlayer.setUpTrack(isTrackPlay)
        isAuto = false
    }

    private fun updateState(state: PlayerStates) {
        isTrackPlay = (state == PlayerStates.STATE_PLAYING)
        currentTrack.state = state
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

    override fun onPlayPauseClick() {
        myPlayer.playPause()
    }

    override fun onSeekBarPositionChanged(position: Long) {
        viewModelScope.launch { myPlayer.seekToPosition(position) }
    }

    override fun onCleared() {
        super.onCleared()
        myPlayer.releasePlayer()
    }
}