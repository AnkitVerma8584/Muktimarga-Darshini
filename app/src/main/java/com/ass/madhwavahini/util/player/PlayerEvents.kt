package com.ass.madhwavahini.util.player

/**
 * An interface for handling player events such as play, pause, next, previous, and seek bar position changes.
 */
interface PlayerEvents {

    fun onSeekForward()
    fun onSeekBackward()
    fun onPlayPauseClick()

    fun onSeekBarPositionChanged(position: Long)
}
