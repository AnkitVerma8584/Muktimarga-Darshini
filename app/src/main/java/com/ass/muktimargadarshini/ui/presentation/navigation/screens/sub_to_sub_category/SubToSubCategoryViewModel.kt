package com.ass.muktimargadarshini.presentation.ui.navigation.screens.sub_to_sub_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.repository.remote.SubToSubCategoryRemoteRepository
import com.ass.muktimargadarshini.domain.utils.Resource
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.files.modals.FilesData
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.files.modals.FilesState
import com.ass.muktimargadarshini.util.print
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubToSubCategoryViewModel @Inject constructor(
    private val subToSubCategoryRemoteRepository: SubToSubCategoryRemoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query get() = _query.asStateFlow()

    private val _subToSubCategoryState = MutableStateFlow(SubToSubCategoryState())

    private val _filesList = MutableStateFlow(emptyList<FilesData>())


    val subToSubCategoryState = combine(_subToSubCategoryState, query) { state, query ->
        state.copy(
            data = state.data?.let {
                it.filter { item -> item.name.contains(query, ignoreCase = true) }
            }
        )
    }.flowOn(Dispatchers.Default)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            SubToSubCategoryState()
        )

    private val _fileState = MutableStateFlow(FilesState())

    val fileState = combine(_fileState, query) { state, query ->
        state.copy(
            data = state.data?.let {
                it.filter { item -> item.name.contains(query, ignoreCase = true) }
            }
        )
    }.flowOn(Dispatchers.Default)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            FilesState()
        )

    val fileData = combine(_filesList, query) { data, query ->
        if (query.length > 2)
            data.map { fileData ->
                fileData.copy(
                    file_data = fileData.file_data.filter { text ->
                        text.text?.contains(query, true) ?: false
                    }
                )
            }.filter {
                it.file_data.isNotEmpty()
            }
        else emptyList()
    }.flowOn(Dispatchers.Default)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            emptyList()
        )

    init {
        viewModelScope.launch(Dispatchers.Default) {
            launch {
                getSubToSubCategoryData(
                    savedStateHandle.get<Int>("cat_id") ?: 0,
                    savedStateHandle.get<Int>("sub_cat_id") ?: 0
                )
            }
            launch {
                getFilesData(
                    savedStateHandle.get<Int>("cat_id") ?: 0,
                    savedStateHandle.get<Int>("sub_cat_id") ?: 0
                )
            }
        }
    }

    private suspend fun getSubToSubCategoryData(catId: Int, subCatId: Int) {
        subToSubCategoryRemoteRepository.getSubToSubCategories(catId, subCatId).collectLatest {
            when (it) {
                is Resource.Cached -> {
                    _subToSubCategoryState.update { state ->
                        state.copy(isLoading = false, data = it.result)
                    }
                }
                is Resource.Failure -> {
                    _subToSubCategoryState.update { state ->
                        state.copy(isLoading = false, error = it.error)
                    }
                }
                Resource.Loading -> {
                    _subToSubCategoryState.update { state ->
                        state.copy(isLoading = true, error = null, data = null)
                    }
                }
                is Resource.Success -> {
                    _subToSubCategoryState.update { state ->
                        state.copy(isLoading = false, error = null, data = it.result)
                    }
                }
            }
        }
    }

    private suspend fun getFilesData(catId: Int, subCatId: Int) {
        subToSubCategoryRemoteRepository.getFiles(catId, subCatId).collectLatest {
            when (it) {
                is Resource.Cached -> {
                    getFilesData(it.result)
                    _fileState.update { state ->
                        state.copy(isLoading = false, data = it.result)
                    }
                }
                is Resource.Failure -> {
                    _fileState.update { state ->
                        state.copy(isLoading = false, error = it.error)
                    }
                }
                Resource.Loading -> {
                    _fileState.update { state ->
                        state.copy(isLoading = true, error = null, data = null)
                    }
                }
                is Resource.Success -> {
                    getFilesData(it.result)
                    _fileState.update { state ->
                        state.copy(isLoading = false, error = null, data = it.result)
                    }
                }
            }
        }
    }

    private suspend fun getFilesData(list: List<HomeFiles>) {
        subToSubCategoryRemoteRepository.getFilesData(list).collectLatest {
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