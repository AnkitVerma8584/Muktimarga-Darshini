package com.ass.muktimargadarshini.ui.presentation.navigation.screens.sub_to_sub_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.repository.remote.SubToSubCategoryRemoteRepository
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesData
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesState
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.sub_to_sub_category.modal.SubToSubCategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
class SubToSubCategoryViewModel @Inject constructor(
    private val subToSubCategoryRemoteRepository: SubToSubCategoryRemoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query get() = _query.asStateFlow()


    private val _subToSubCategoryState = MutableStateFlow(SubToSubCategoryState())
    val subToSubCategoryState = combine(_subToSubCategoryState, query) { state, query ->
        state.copy(data = state.data?.let {
            it.filter { item -> item.name.contains(query, ignoreCase = true) }
        })
    }.flowOn(Dispatchers.Default).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        SubToSubCategoryState()
    )


    private val _fileState = MutableStateFlow(FilesState())
    val fileState = combine(_fileState, query) { state, query ->
        state.copy(data = state.data?.let {
            it.filter { item -> item.name.contains(query, ignoreCase = true) }
        })
    }.flowOn(Dispatchers.Default).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), FilesState()
    )

    private val _filesList = MutableStateFlow(emptyList<FilesData>())

    val searchedFilesData = combine(_filesList, query) { data, query ->
        if (query.length > 2) data.map { fileData ->
            fileData.copy(fileData = fileData.fileData.filter { text ->
                text.text?.contains(query, true) ?: false
            })
        }.filter {
            it.fileData.isNotEmpty()
        }
        else emptyList()
    }.flowOn(Dispatchers.Default).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), emptyList()
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
                getFiles(
                    savedStateHandle.get<Int>("cat_id") ?: 0,
                    savedStateHandle.get<Int>("sub_cat_id") ?: 0
                )
            }
        }
    }

    private suspend fun getSubToSubCategoryData(catId: Int, subCatId: Int) {
        subToSubCategoryRemoteRepository.getSubToSubCategories(catId, subCatId).collectLatest {
            _subToSubCategoryState.value = it
        }
    }

    private suspend fun getFiles(catId: Int, subCatId: Int) {
        subToSubCategoryRemoteRepository.getFiles(catId, subCatId).collectLatest {
            _fileState.value = it
            it.data?.let { list -> getDataFromFiles(list) }
        }
    }

    private suspend fun getDataFromFiles(list: List<HomeFiles>) {
        subToSubCategoryRemoteRepository.getFilesData(list).collectLatest {
            _filesList.value = it
        }
    }

    fun queryChanged(newQuery: String = "") {
        _query.value = newQuery
    }
}