package com.ass.madhwavahini.ui.presentation.navigation.screens.sub_to_sub_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.domain.modals.HomeSubToSubCategory
import com.ass.madhwavahini.domain.repository.SubToSubCategoryRepository
import com.ass.madhwavahini.domain.wrapper.UiStateList
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesData
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
    private val subToSubCategoryRepository: SubToSubCategoryRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query get() = _query.asStateFlow()


    private val _subToSubCategoryState = MutableStateFlow(UiStateList<HomeSubToSubCategory>())
    val subToSubCategoryState = combine(_subToSubCategoryState, query) { state, query ->
        state.copy(data = state.data?.let {
            it.filter { item -> item.name.contains(query, ignoreCase = true) }
        })
    }.flowOn(Dispatchers.Default).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        UiStateList()
    )

    private val _fileState = MutableStateFlow(UiStateList<HomeFile>())
    val fileState = combine(_fileState, query) { state, query ->
        state.copy(data = state.data?.let {
            it.filter { item -> item.name.contains(query, ignoreCase = true) }
        })
    }.flowOn(Dispatchers.Default).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L), UiStateList()
    )

    private val _filesList = MutableStateFlow(emptyList<FilesData>())

    val searchedFilesData = combine(_filesList, query) { data, query ->
        if (query.length > 2) data.map { fileData ->
            fileData.copy(fileData = fileData.fileData.filter { text ->
                text.text.contains(query, true)
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
        subToSubCategoryRepository.getSubToSubCategories(catId, subCatId).collectLatest {
            _subToSubCategoryState.value = it
        }
    }

    private suspend fun getFiles(catId: Int, subCatId: Int) {
        subToSubCategoryRepository.getFiles(catId, subCatId).collectLatest {
            _fileState.value = it
            it.data?.let { list -> getDataFromFiles(list) }
        }
    }

    private suspend fun getDataFromFiles(list: List<HomeFile>) {
        subToSubCategoryRepository.getFilesData(list).collectLatest {
            _filesList.value = it
        }
    }

    fun queryChanged(newQuery: String = "") {
        _query.value = newQuery
    }
}