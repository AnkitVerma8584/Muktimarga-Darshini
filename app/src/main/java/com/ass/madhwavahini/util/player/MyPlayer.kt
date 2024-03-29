package com.ass.madhwavahini.util.player

import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.ass.madhwavahini.util.player.PlayerStates.STATE_BUFFERING
import com.ass.madhwavahini.util.player.PlayerStates.STATE_END
import com.ass.madhwavahini.util.player.PlayerStates.STATE_ERROR
import com.ass.madhwavahini.util.player.PlayerStates.STATE_IDLE
import com.ass.madhwavahini.util.player.PlayerStates.STATE_PAUSE
import com.ass.madhwavahini.util.player.PlayerStates.STATE_PLAYING
import com.ass.madhwavahini.util.player.PlayerStates.STATE_READY
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


class MyPlayer @Inject constructor(private val player: ExoPlayer) : Player.Listener {

    val playerState = MutableStateFlow(STATE_IDLE)

    val currentPlaybackPosition: Long
        get() = if (player.currentPosition > 0) player.currentPosition else 0L

    val currentTrackDuration: Long
        get() = if (player.duration > 0) player.duration else 0L

    fun initPlayer(url: String) {
        player.addListener(this)
        player.setMediaItem(MediaItem.fromUri(url))
        player.prepare()
    }

    fun seekForward() {
        player.seekForward()
    }

    fun seekBackward() {
        player.seekBack()
    }

    fun playPause() {
        if (player.playbackState == Player.STATE_IDLE)
            player.prepare()

        player.playWhenReady = !player.playWhenReady
    }

    fun releasePlayer() {
        player.release()
    }

    fun seekToPosition(position: Long) {
        player.seekTo(position)
    }


    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        playerState.tryEmit(STATE_ERROR)
    }

    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        if (player.playbackState == Player.STATE_READY) {
            if (playWhenReady) {
                playerState.tryEmit(STATE_PLAYING)
            } else {
                playerState.tryEmit(STATE_PAUSE)
            }
        }
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        if (reason == Player.MEDIA_ITEM_TRANSITION_REASON_AUTO) {
            playerState.tryEmit(STATE_PLAYING)
        }
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            Player.STATE_IDLE -> {
                playerState.tryEmit(STATE_IDLE)
            }

            Player.STATE_BUFFERING -> {
                playerState.tryEmit(STATE_BUFFERING)
            }

            Player.STATE_READY -> {
                playerState.tryEmit(STATE_READY)
                if (player.playWhenReady) {
                    playerState.tryEmit(STATE_PLAYING)
                } else {
                    playerState.tryEmit(STATE_PAUSE)
                }
            }

            Player.STATE_ENDED -> {
                playerState.tryEmit(STATE_END)
            }
        }
    }
}