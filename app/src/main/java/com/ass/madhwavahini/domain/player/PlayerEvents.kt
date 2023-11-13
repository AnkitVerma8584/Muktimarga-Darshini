package com.ass.madhwavahini.domain.player

/**
 * An interface for handling player events such as play, pause, next, previous, and seek bar position changes.
 */
interface PlayerEvents {

    fun onPlayPauseClick()

    fun onSeekBarPositionChanged(position: Long)
}
