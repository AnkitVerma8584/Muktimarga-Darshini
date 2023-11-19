package com.ass.madhwavahini.ui.presentation.navigation.screens.pdf

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.ass.madhwavahini.ui.presentation.common.ShowError
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.github.barteksc.pdfviewer.PDFView

@Composable
fun PdfScreen(
    pdfViewModel: PdfViewModel = hiltViewModel()
) {
    val pdfState by pdfViewModel.pdfState.collectAsState()
    if (pdfState.isLoading)
        Loading()
    else
        Column(modifier = Modifier.fillMaxSize()) {
            pdfState.error?.ShowError()
            pdfState.data?.let { pdf ->
                AndroidView(
                    factory = {
                        PDFView(it, null).also { pdfView ->
                            pdfView.fromFile(pdf)
                                .onPageError { _, t -> pdfViewModel.setPdfError(t) }
                                .onError(pdfViewModel::setPdfError)
                                .load()
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
}
