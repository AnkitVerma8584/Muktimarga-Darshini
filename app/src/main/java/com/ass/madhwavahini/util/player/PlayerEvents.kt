package com.ass.madhwavahini.util.player

interface PlayerEvents {
    fun onSeekForward()
    fun onSeekBackward()
    fun onPlayPauseClick()
    fun onSeekBarPositionChanged(position: Long)
}
