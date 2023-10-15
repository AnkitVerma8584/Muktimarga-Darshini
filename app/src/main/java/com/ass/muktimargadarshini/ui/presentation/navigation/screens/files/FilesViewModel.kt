package com.ass.muktimargadarshini.ui.presentation.navigation.screens.files

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.repository.remote.FilesRemoteRepository
import com.ass.muktimargadarshini.domain.utils.Resource
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesData
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesState
import com.ass.muktimargadarshini.util.print
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(
    private val filesRemoteRepository: FilesRemoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query get() = _query.asStateFlow()

    private val _state = MutableStateFlow(FilesState())

    private val _filesList = MutableStateFlow(emptyList<FilesData>())

    val fileState = combine(_state, query) { state, query ->
        state.copy(data = state.data?.let {
            it.filter { item -> item.name.contains(query, ignoreCase = true) }
        })
    }.flowOn(Default).stateIn(viewModelScope, SharingStarted.WhileSubscribed(), FilesState())

    val fileData = combine(_filesList, query) { data, query ->
        if (query.length > 2)
            data.map { fileData ->
                fileData.copy(
                    fileData = fileData.fileData.filter { text ->
                        text.text?.contains(query, true) ?: false
                    }
                )
            }.filter {
                it.fileData.isNotEmpty()
            }
        else emptyList()
    }.flowOn(Default).stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    init {
        viewModelScope.launch(Default) {
            getSubCategoryData(
                savedStateHandle.get<Int>("cat_id") ?: 0,
                savedStateHandle.get<Int>("sub_cat_id") ?: 0,
                savedStateHandle.get<Int>("sub_to_sub_cat_id") ?: 0,
            )
        }
    }

    private suspend fun getSubCategoryData(catId: Int, subCatId: Int, subToSubCatId: Int) {
        filesRemoteRepository.getFiles(catId, subCatId, subToSubCatId).collectLatest {
            when (it) {
                is Resource.Cached -> {
                    getFilesData(it.result)
                    _state.update { state ->
                        state.copy(isLoading = false, data = it.result)
                    }
                }
                is Resource.Failure -> {
                    _state.update { state ->
                        state.copy(isLoading = false, error = it.error)
                    }
                }
                Resource.Loading -> {
                    _state.update { state ->
                        state.copy(isLoading = true, error = null, data = null)
                    }
                }
                is Resource.Success -> {
                    getFilesData(it.result)
                    _state.update { state ->
                        state.copy(isLoading = false, error = null, data = it.result)
                    }
                }
            }
        }
    }

    private suspend fun getFilesData(list: List<HomeFiles>) {
        filesRemoteRepository.getFilesData(list).collectLatest {
            when (it) {
                is Resource.Cached -> {

                }
                is Resource.Failure -> {
                    it.error.print()
                }
                Resource.Loading -> {

                }
                is Resource.Success -> {
                    _filesList.value = it.result
                }
            }
        }
    }

    fun queryChanged(newQuery: String = "") {
        _query.value = newQuery
    }
}