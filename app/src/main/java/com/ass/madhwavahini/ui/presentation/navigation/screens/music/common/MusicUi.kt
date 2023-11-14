package com.ass.madhwavahini.ui.presentation.navigation.screens.music.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.domain.modals.Track
import com.ass.madhwavahini.domain.player.PlaybackState
import com.ass.madhwavahini.domain.player.PlayerStates
import com.ass.madhwavahini.util.formatTime
import kotlinx.coroutines.flow.StateFlow


@Composable
fun TrackInfo(trackName: String, artistName: String) {
    Text(
        text = trackName,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    )
    Text(
        text = artistName,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    )
}

@Composable
fun TrackProgressSlider(
    playbackState: StateFlow<PlaybackState>,
    onSeekBarPositionChanged: (Long) -> Unit
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
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = playbackStateValue.currentTrackDuration.formatTime(),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun TrackControls(
    selectedTrack: Track,
    onPlayPauseClick: () -> Unit,
    onSeekForward: () -> Unit,
    onSeekBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        PreviousIcon(onClick = onSeekBack)
        Spacer(modifier = Modifier.width(50.dp))
        PlayPauseIcon(
            selectedTrack = selectedTrack, onClick = onPlayPauseClick
        )
        Spacer(modifier = Modifier.width(50.dp))
        NextIcon(onClick = onSeekForward)
    }
}


@Composable
fun PreviousIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.FastRewind,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun PlayPauseIcon(selectedTrack: Track, onClick: () -> Unit) {
    if (selectedTrack.state == PlayerStates.STATE_BUFFERING) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size = 48.dp)
                .padding(all = 9.dp),
            color = MaterialTheme.colorScheme.onBackground,
        )
    } else {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = if (selectedTrack.state == PlayerStates.STATE_PLAYING)
                    Icons.Default.Pause
                else Icons.Default.PlayArrow,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Composable
fun NextIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.FastForward,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}