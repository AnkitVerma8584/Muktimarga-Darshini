package com.ass.madhwavahini.ui.presentation.navigation.screens.files

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.domain.modals.HomeFiles
import com.ass.madhwavahini.domain.repository.FilesRepository
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesData
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(
    private val filesRepository: FilesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query get() = _query.asStateFlow()

    private val _state = MutableStateFlow(FilesState())

    private val _filesList = MutableStateFlow(emptyList<FilesData>())

    val fileState = combine(_state, query) { state, query ->
        state.copy(
            data = state.data?.let {
                it.filter { item -> item.name.contains(query, ignoreCase = true) }
            })
    }.flowOn(Default).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), FilesState()
    )

    val fileData = combine(_filesList, query) { data, query ->
        if (query.length > 2) data.map { fileData ->
            fileData.copy(fileData = fileData.fileData.filter { text ->
                text.text?.contains(query, true) ?: false
            })
        }.filter {
            it.fileData.isNotEmpty()
        }
        else emptyList()
    }.flowOn(Default).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch(Default) {
            getFilesList(
                savedStateHandle.get<Int>("cat_id") ?: 0,
                savedStateHandle.get<Int>("sub_cat_id") ?: 0,
                savedStateHandle.get<Int>("sub_to_sub_cat_id") ?: 0,
            )
        }
    }

    private suspend fun getFilesList(catId: Int, subCatId: Int, subToSubCatId: Int) {
        filesRepository.getFiles(catId, subCatId, subToSubCatId).collect {
            _state.value = it
            it.data?.let { list ->
                getFilesData(list)
            }
        }
    }

    private suspend fun getFilesData(list: List<HomeFiles>) {
        filesRepository.getFilesData(list).collectLatest {
            it.data?.let { list ->
                _filesList.value = list
            }
        }
    }

    fun queryChanged(newQuery: String = "") {
        _query.value = newQuery
    }
}