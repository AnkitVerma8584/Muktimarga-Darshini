package com.ass.muktimargadarshini.presentation.ui.navigation.screens.pdf

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.muktimargadarshini.domain.repository.remote.FileDataRemoteRepository
import com.ass.muktimargadarshini.domain.utils.Resource
import com.ass.muktimargadarshini.domain.utils.StringUtil
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
    private val filesRepository: FileDataRemoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _pdfState = MutableStateFlow(PdfState())
    val pdfState = _pdfState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchFile(
                savedStateHandle.get<Int>("file_id") ?: 0,
                savedStateHandle.get<String>("file_name") ?: "PdfFile",
                savedStateHandle.get<String>("file_url") ?: "1666939107.pdf"
            )
        }
    }

    private suspend fun fetchFile(homeFileId: Int, homeFileName: String, homeFileUrl: String) {
        filesRepository.getFileData("${homeFileName}_${homeFileId}.pdf", homeFileUrl)
            .collectLatest { result ->
                when (result) {
                    is Resource.Cached ->
                        _pdfState.update {
                            it.copy(isLoading = false, error = null, file = result.result)
                        }

                    is Resource.Failure -> _pdfState.update {
                        it.copy(isLoading = false, error = result.error, file = null)
                    }

                    Resource.Loading ->
                        _pdfState.update {
                            it.copy(isLoading = true, error = null, file = null)
                        }
                    is Resource.Success ->
                        _pdfState.update {
                            it.copy(isLoading = false, error = null, file = result.result)
                        }
                }
            }
    }

    fun setPdfError(throwable: Throwable) {
        _pdfState.update {
            it.copy(
                isLoading = false,
                error = StringUtil.DynamicText(throwable.message ?: "Unable to render pdf"),
                file = null
            )
        }
    }
}