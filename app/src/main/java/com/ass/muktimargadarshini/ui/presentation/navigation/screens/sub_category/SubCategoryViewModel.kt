package com.ass.muktimargadarshini.ui.presentation.navigation.screens.sub_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.muktimargadarshini.domain.repository.SubCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SubCategoryViewModel @Inject constructor(
    subCategoryRepository: SubCategoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _subCategoryQuery = MutableStateFlow("")
    val subCategoryQuery get() = _subCategoryQuery.asStateFlow()

    private val _state =
        subCategoryRepository.getSubCategory(savedStateHandle.get<Int>("cat_id") ?: 0).flowOn(IO)

    val subCategoryState = combine(_state, subCategoryQuery) { state, query ->
        state.copy(
            data = state.data?.let {
                it.filter { item -> item.name.contains(query, ignoreCase = true) }
            }
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        SubCategoryState()
    )

    fun queryChanged(newQuery: String = "") {
        _subCategoryQuery.value = newQuery
    }
}