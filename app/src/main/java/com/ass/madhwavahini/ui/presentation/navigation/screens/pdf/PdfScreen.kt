package com.ass.madhwavahini.ui.presentation.navigation.screens.pdf

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.ShowError
import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.components.AudioToggleButton
import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.components.BottomMusicBar
import com.github.barteksc.pdfviewer.PDFView

@Composable
fun PdfScreen(
    pdfViewModel: PdfViewModel = hiltViewModel()
) {
    val pdfState by pdfViewModel.pdfState.collectAsState()

    var isDisplayingAudio by rememberSaveable {
        mutableStateOf(false)
    }

    if (pdfState.isLoading) Loading()
    else {
        Box {
            Column(modifier = Modifier.fillMaxSize()) {
                pdfState.error?.ShowError()
                pdfState.data?.let { pdf ->
                    AndroidView(
                        factory = {
                            PDFView(it, null).also { pdfView ->
                                pdfView.fromFile(pdf)
                                    .onPageError { _, t -> pdfViewModel.setPdfError(t) }
                                    .onError(pdfViewModel::setPdfError).load()
                            }
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
                if (pdfViewModel.hasAudioFile)
                    BottomMusicBar(
                        selectedTrack = pdfViewModel.currentTrack,
                        playbackState = pdfViewModel.playbackState,
                        onSeekBarPositionChanged = pdfViewModel::onSeekBarPositionChanged,
                        onPlayPauseClick = pdfViewModel::onPlayPauseClick,
                        onSeekForward = pdfViewModel::onSeekForward,
                        onSeekBackward = pdfViewModel::onSeekBackward,
                        isDisplayingAudio = isDisplayingAudio
                    )
            }
            if (pdfViewModel.hasAudioFile)
                AudioToggleButton(isDisplayingAudio = isDisplayingAudio) {
                    isDisplayingAudio = !isDisplayingAudio
                }

        }

    }
}
