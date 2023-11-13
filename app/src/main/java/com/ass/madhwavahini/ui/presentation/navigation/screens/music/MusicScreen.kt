package com.ass.madhwavahini.ui.presentation.navigation.screens.music

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ass.madhwavahini.ui.presentation.navigation.screens.music.common.TrackControls
import com.ass.madhwavahini.ui.presentation.navigation.screens.music.common.TrackInfo
import com.ass.madhwavahini.ui.presentation.navigation.screens.music.common.TrackProgressSlider

@Composable
fun MusicScreen(
    musicViewModel: MusicViewModel = hiltViewModel(),
    windowSizeClass: WindowSizeClass,
) {

    val configuration = LocalConfiguration.current
    val shouldDisplayLandscape =
        (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) && (windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact)

    if (shouldDisplayLandscape) MusicLayoutLandscape(musicViewModel)
    else MusicLayoutPortrait(musicViewModel)
}

@Composable
private fun MusicLayoutPortrait(
    musicViewModel: MusicViewModel
) {
    val selectedTrack = musicViewModel.currentTrack

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(all = 16.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            painter = painterResource(id = selectedTrack.trackImage),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(5.dp))
        TrackInfo(
            trackName = selectedTrack.trackName, artistName = selectedTrack.artistName
        )
        Spacer(modifier = Modifier.height(5.dp))
        TrackProgressSlider(playbackState = musicViewModel.playbackState) {
            musicViewModel.onSeekBarPositionChanged(it)
        }
        Spacer(modifier = Modifier.height(10.dp))
        TrackControls(
            selectedTrack = selectedTrack,
            onPlayPauseClick = musicViewModel::onPlayPauseClick,
            onSeekForward = musicViewModel::onSeekForward,
            onSeekBack = musicViewModel::onSeekBackward
        )
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun MusicLayoutLandscape(
    musicViewModel: MusicViewModel
) {
    val selectedTrack = musicViewModel.currentTrack

    Row {
        Image(
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f)
                .padding(all = 16.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            painter = painterResource(id = selectedTrack.trackImage),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight(), verticalArrangement = Arrangement.Bottom
        ) {
            TrackInfo(
                trackName = selectedTrack.trackName, artistName = selectedTrack.artistName
            )
            TrackProgressSlider(playbackState = musicViewModel.playbackState) {
                musicViewModel.onSeekBarPositionChanged(it)
            }
            TrackControls(
                selectedTrack = selectedTrack,
                onPlayPauseClick = musicViewModel::onPlayPauseClick,
                onSeekForward = musicViewModel::onSeekForward,
                onSeekBack = musicViewModel::onSeekBackward
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }


}
