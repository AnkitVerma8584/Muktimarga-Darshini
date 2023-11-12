package com.ass.madhwavahini.ui.presentation.navigation.screens.pdf

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.domain.repository.DocumentRepository
import com.ass.madhwavahini.domain.utils.StringUtil
import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.modals.DocumentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PdfViewModel @Inject constructor(
    private val filesRepository: DocumentRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _pdfState = MutableStateFlow(DocumentState())
    val pdfState = _pdfState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchFile(
                savedStateHandle.get<Int>("file_id") ?: 0,
                savedStateHandle.get<String>("file_url") ?: ""
            )
        }
    }

    private suspend fun fetchFile(homeFileId: Int, homeFileUrl: String) {
        filesRepository.getDocument("file_${homeFileId}.pdf", homeFileUrl)
            .collectLatest { result ->
                _pdfState.value = result
            }
    }

    fun setPdfError(throwable: Throwable) {
        _pdfState.update {
            it.copy(
                isLoading = false,
                error = StringUtil.DynamicText(throwable.message ?: "Unable to render pdf"),
                data = null
            )
        }
    }
}