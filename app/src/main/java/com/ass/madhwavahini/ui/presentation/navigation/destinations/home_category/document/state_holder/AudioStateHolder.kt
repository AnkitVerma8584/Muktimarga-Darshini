package com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.document.state_holder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import com.ass.madhwavahini.data.remote.Api.getAudioUrl
import com.ass.madhwavahini.domain.modals.Track
import com.ass.madhwavahini.util.player.MyPlayer
import com.ass.madhwavahini.util.player.PlaybackState
import com.ass.madhwavahini.util.player.PlayerEvents
import com.ass.madhwavahini.util.player.PlayerStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AudioStateHolder(
    private val scope:CoroutineScope,
    private val myPlayer: MyPlayer,
    private val savedStateHandle: SavedStateHandle
) : PlayerEvents {

    var currentTrack: Track by mutableStateOf(getTrackInfo())
        private set

    var hasAudioFile: Boolean by mutableStateOf(false)
        private set

    private var isTrackPlay: Boolean = false

    private var playbackStateJob: Job? = null

    private val _playbackState = MutableStateFlow(PlaybackState(0L, 0L))
    val playbackState: StateFlow<PlaybackState> get() = _playbackState.asStateFlow()


    init {
        if (currentTrack.trackUrl.isNotBlank()) {
            hasAudioFile = true
            myPlayer.initPlayer(currentTrack.trackUrl)
            observePlayerState()
        }
    }
    private fun getTrackInfo(): Track {
        val audioUrl = savedStateHandle.get<String>("audio_url").getAudioUrl()
        val audioImage = savedStateHandle.get<String>("audio_image").getAudioUrl()
        return if (audioUrl.isBlank()) Track() else Track(
            trackUrl = audioUrl, trackImage = audioImage
        )
    }

    private fun updateState(state: PlayerStates) {
        isTrackPlay = (state == PlayerStates.STATE_PLAYING)
        currentTrack = currentTrack.copy(state = state)
        updatePlaybackState(state)
    }

    private fun observePlayerState() {
        scope.launch {
            myPlayer.playerState.collect {
                updateState(it)
            }
        }
    }


    private fun updatePlaybackState(state: PlayerStates) {
        playbackStateJob?.cancel()
        playbackStateJob = scope.launch {
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

    fun clearPlayer(){
        myPlayer.releasePlayer()
    }
}