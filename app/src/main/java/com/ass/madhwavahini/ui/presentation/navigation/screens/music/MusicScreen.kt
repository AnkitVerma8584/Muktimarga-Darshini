package com.ass.madhwavahini.ui.presentation.navigation.screens.music

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.Track
import com.ass.madhwavahini.domain.player.PlaybackState
import com.ass.madhwavahini.domain.player.PlayerStates
import com.ass.madhwavahini.ui.theme.md_theme_light_onPrimary
import com.ass.madhwavahini.ui.theme.md_theme_light_onPrimaryContainer
import com.ass.madhwavahini.ui.theme.md_theme_light_surfaceVariant
import kotlinx.coroutines.flow.StateFlow

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun MusicScreen(
    musicViewModel: MusicViewModel = hiltViewModel()
) {
    val selectedTrack = musicViewModel.currentTrack
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TrackInfo(
            trackImage = selectedTrack.trackImage,
            trackName = selectedTrack.trackName,
            artistName = selectedTrack.artistName
        )
        TrackProgressSlider(playbackState = musicViewModel.playbackState) {
            musicViewModel.onSeekBarPositionChanged(it)
        }
        TrackControls(
            selectedTrack = selectedTrack,
            onPlayPauseClick = musicViewModel::onPlayPauseClick,
        )
    }
}

@Composable
fun TrackInfo(trackImage: Int, trackName: String, artistName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 350.dp)
            .background(md_theme_light_surfaceVariant)
    ) {
        TrackImage(
            trackImage = trackImage, modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp)
        )
    }
    Text(
        text = trackName,
        style = typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    )
    Text(
        text = artistName,
        style = typography.bodySmall,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    )
}

@Composable
fun TrackProgressSlider(
    playbackState: StateFlow<PlaybackState>, onSeekBarPositionChanged: (Long) -> Unit
) {
    val playbackStateValue = playbackState.collectAsState(
        initial = PlaybackState(0L, 0L)
    ).value
    var currentMediaProgress = playbackStateValue.currentPlaybackPosition.toFloat()
    var currentPosTemp by rememberSaveable { mutableFloatStateOf(0f) }

    Slider(
        value = if (currentPosTemp == 0f) currentMediaProgress else currentPosTemp,
        onValueChange = { currentPosTemp = it },
        onValueChangeFinished = {
            currentMediaProgress = currentPosTemp
            currentPosTemp = 0f
            onSeekBarPositionChanged(currentMediaProgress.toLong())
        },
        valueRange = 0f..playbackStateValue.currentTrackDuration.toFloat(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = playbackStateValue.currentPlaybackPosition.formatTime(),
            style = typography.bodySmall
        )
        Text(
            text = playbackStateValue.currentTrackDuration.formatTime(),
            style = typography.bodySmall
        )
    }
}

@Composable
fun TrackControls(
    selectedTrack: Track, onPlayPauseClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PreviousIcon(onClick = {}, isBottomTab = false)
        PlayPauseIcon(
            selectedTrack = selectedTrack, onClick = onPlayPauseClick, isBottomTab = false
        )
        NextIcon(onClick = {}, isBottomTab = false)
    }
}

fun Long.formatTime(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

@Composable
fun TrackImage(
    trackImage: Int, modifier: Modifier
) {
    Image(
        modifier = modifier.clip(shape = RoundedCornerShape(8.dp)),
        painter = painterResource(id = trackImage),
        contentDescription = null
    )
}

/**
 * A composable function that displays the name of a track.
 *
 * @param trackName The name of the track.
 * @param modifier The modifier to be applied to the Text.
 */
@Composable
fun TrackName(trackName: String, modifier: Modifier) {
    androidx.compose.material3.Text(
        text = trackName,
        style = typography.bodyLarge,
        color = md_theme_light_onPrimary,
        modifier = modifier.padding(start = 16.dp, end = 8.dp)
    )
}

/**
 * A composable function that displays a "Previous" icon.
 *
 * @param onClick The action to be performed when the icon is clicked.
 * @param isBottomTab A boolean indicating whether the icon is part of the bottom tab.
 */
@Composable
fun PreviousIcon(onClick: () -> Unit, isBottomTab: Boolean) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_pdf),
            contentDescription = null,
            tint = if (isBottomTab) md_theme_light_onPrimary else md_theme_light_onPrimaryContainer,
            modifier = Modifier.size(48.dp)
        )
    }
}

/**
 * A composable function that displays a "Play/Pause" icon.
 * Displays a loading spinner when the track is buffering.
 *
 * @param selectedTrack The currently selected track.
 * @param onClick The action to be performed when the icon is clicked.
 * @param isBottomTab A boolean indicating whether the icon is part of the bottom tab.
 */
@Composable
fun PlayPauseIcon(selectedTrack: Track, onClick: () -> Unit, isBottomTab: Boolean) {
    if (selectedTrack.state == PlayerStates.STATE_BUFFERING) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size = 48.dp)
                .padding(all = 9.dp),
            color = if (isBottomTab) md_theme_light_onPrimary else md_theme_light_onPrimaryContainer,
        )
    } else {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(
                    id = if (selectedTrack.state == PlayerStates.STATE_PLAYING) R.drawable.ic_home
                    else R.drawable.ic_contact
                ),
                contentDescription = stringResource(id = R.string.category),
                tint = if (isBottomTab) md_theme_light_onPrimary else md_theme_light_onPrimaryContainer,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

/**
 * A composable function that displays a "Next" icon.
 *
 * @param onClick The action to be performed when the icon is clicked.
 * @param isBottomTab A boolean indicating whether the icon is part of the bottom tab.
 */
@Composable
fun NextIcon(onClick: () -> Unit, isBottomTab: Boolean) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_pdf),
            contentDescription = null,
            tint = if (isBottomTab) md_theme_light_onPrimary else md_theme_light_onPrimaryContainer,
            modifier = Modifier.size(48.dp)
        )
    }
}