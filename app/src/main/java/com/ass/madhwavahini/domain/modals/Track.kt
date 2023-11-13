package com.ass.madhwavahini.domain.modals

import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.player.PlayerStates

data class Track(
    val trackId: Int = 0,
    val trackName: String = "",
    val trackUrl: String = "",
    val trackImage: Int = R.drawable.app_logo,
    val artistName: String = "",
    var state: PlayerStates = PlayerStates.STATE_IDLE
)