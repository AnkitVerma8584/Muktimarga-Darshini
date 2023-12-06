package com.ass.madhwavahini.domain.modals

import com.ass.madhwavahini.util.player.PlayerStates

data class Track(
    val trackUrl: String = "",
    val trackImage: String = "",
    var state: PlayerStates = PlayerStates.STATE_IDLE
)