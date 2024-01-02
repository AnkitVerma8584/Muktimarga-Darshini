package com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.document.pdf

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.domain.repository.DocumentRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.domain.wrapper.UiState
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.document.state_holder.AudioStateHolder
import com.ass.madhwavahini.util.player.MyPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PdfViewModel @Inject constructor(
    private val filesRepository: DocumentRepository,
    private val savedStateHandle: SavedStateHandle,
    myPlayer: MyPlayer
) : ViewModel() {

    private val _pdfState = MutableStateFlow(UiState<File>())
    val pdfState = _pdfState.asStateFlow()

    val audioStateHolder: AudioStateHolder =
        AudioStateHolder(viewModelScope, myPlayer, savedStateHandle)

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

    override fun onCleared() {
        super.onCleared()
        audioStateHolder.clearPlayer()
    }
}