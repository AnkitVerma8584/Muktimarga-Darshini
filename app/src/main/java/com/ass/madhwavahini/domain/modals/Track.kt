package com.ass.madhwavahini.domain.modals

import com.ass.madhwavahini.util.player.PlayerStates

data class Track(
    val trackId: Int = 0,
    val trackName: String = "",
    val trackUrl: String = "",
    val trackImage: String = "",
    val artistName: String = "",
    var state: PlayerStates = PlayerStates.STATE_IDLE
)