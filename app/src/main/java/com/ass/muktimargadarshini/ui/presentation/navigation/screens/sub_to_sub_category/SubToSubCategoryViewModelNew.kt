package com.ass.muktimargadarshini.ui.presentation.navigation.screens.sub_to_sub_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ass.muktimargadarshini.domain.modals.filter.FileFilter
import com.ass.muktimargadarshini.domain.repository.SubToSubCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SubToSubCategoryViewModelNew @Inject constructor(
    private val subToSubCategoryRepository: SubToSubCategoryRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val catId = savedStateHandle.get<Int>("cat_id") ?: 0
    private val subCatId = savedStateHandle.get<Int>("sub_cat_id") ?: 0

    private val _subToSubCatState =
        subToSubCategoryRepository.getSubToSubCategories(catId, subCatId).flowOn(IO)

    private val _filesState = subToSubCategoryRepository.getFiles(catId, subCatId).flowOn(IO)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _filesData = _filesState.flatMapLatest {
        subToSubCategoryRepository.getFilesData(it.data)
    }.flowOn(IO)

    private val _authors = subToSubCategoryRepository.getAuthors()
    private val _gods = subToSubCategoryRepository.getGods()

    private val filterData = combine(_authors, _gods) { authors, gods ->
        FileFilter(authors, gods)
    }.flowOn(IO)

    private val filterFiles = combine(_filesState, filterData) { state, filter ->
        state.data?.let { files ->
            files.filter {
                filter.selectedAuthorIds.contains(it.author_id)
                        && filter.selectedGodIds.contains(it.god_id)
            }
        }
    }.distinctUntilChanged().flowOn(Dispatchers.Default)

    private val _query = MutableStateFlow("")
    val query get() = _query.asStateFlow()

    fun queryChanged(newQuery: String = "") {
        _query.value = newQuery
    }
}