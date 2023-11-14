package com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ass.madhwavahini.domain.modals.Track
import com.ass.madhwavahini.domain.player.PlaybackState
import com.ass.madhwavahini.domain.player.PlayerStates
import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.FileDetailsViewModel
import com.ass.madhwavahini.util.formatTime
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BottomMusicBar(
    musicViewModel: FileDetailsViewModel,
    isDisplayingAudio: Boolean
) {
    val selectedTrack = musicViewModel.currentTrack
    AnimatedVisibility(
        modifier = Modifier,
        visible = isDisplayingAudio,
        enter = slideInVertically(
            animationSpec = tween(400),
            initialOffsetY = { it }) + fadeIn(),
        exit = fadeOut(),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.Crop,
                    painter = rememberAsyncImagePainter(model = selectedTrack.trackImage),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(), verticalArrangement = Arrangement.Bottom
                ) {
                    AudioSlider(playbackState = musicViewModel.playbackState) {
                        musicViewModel.onSeekBarPositionChanged(it)
                    }
                    AudioControls(
                        selectedTrack = selectedTrack,
                        onPlayPauseClick = musicViewModel::onPlayPauseClick,
                        onSeekForward = musicViewModel::onSeekForward,
                        onSeekBack = musicViewModel::onSeekBackward
                    )
                }
            }
        })

}


@Composable
private fun AudioSlider(
    playbackState: StateFlow<PlaybackState>,
    onSeekBarPositionChanged: (Long) -> Unit
) {
    val playbackStateValue = playbackState.collectAsState(
        initial = PlaybackState(0L, 0L)
    ).value
    var currentMediaProgress = playbackStateValue.currentPlaybackPosition.toFloat()
    var currentPosTemp by rememberSaveable { mutableFloatStateOf(0f) }

    Slider(
        colors = SliderDefaults.colors(
            activeTrackColor = MaterialTheme.colorScheme.onSecondaryContainer,
            thumbColor = MaterialTheme.colorScheme.onSecondaryContainer,
            inactiveTrackColor = MaterialTheme.colorScheme.primaryContainer
        ),
        value = if (currentPosTemp == 0f) currentMediaProgress else currentPosTemp,
        onValueChange = { currentPosTemp = it },
        onValueChangeFinished = {
            currentMediaProgress = currentPosTemp
            currentPosTemp = 0f
            onSeekBarPositionChanged(currentMediaProgress.toLong())
        },
        valueRange = 0f..playbackStateValue.currentTrackDuration.toFloat(),
        modifier = Modifier.fillMaxWidth()
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            text = playbackStateValue.currentPlaybackPosition.formatTime(),
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            text = playbackStateValue.currentTrackDuration.formatTime(),
            style = MaterialTheme.typography.labelMedium
        )
    }
}


@Composable
private fun AudioControls(
    selectedTrack: Track,
    onPlayPauseClick: () -> Unit,
    onSeekForward: () -> Unit,
    onSeekBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SeekBackward(onClick = onSeekBack)
        PlayPauseIcon(
            selectedTrack = selectedTrack, onClick = onPlayPauseClick
        )
        SeekForward(onClick = onSeekForward)
    }
}


@Composable
private fun SeekBackward(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.FastRewind,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}


@Composable
private fun SeekForward(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.FastForward,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
fun PlayPauseIcon(selectedTrack: Track, onClick: () -> Unit) {
    if (selectedTrack.state == PlayerStates.STATE_BUFFERING) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size = 48.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    } else {
        IconButton(
            modifier = Modifier.size(48.dp),
            onClick = onClick
        ) {
            Icon(
                imageVector = if (selectedTrack.state == PlayerStates.STATE_PLAYING)
                    Icons.Default.Pause
                else Icons.Default.PlayArrow,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}
