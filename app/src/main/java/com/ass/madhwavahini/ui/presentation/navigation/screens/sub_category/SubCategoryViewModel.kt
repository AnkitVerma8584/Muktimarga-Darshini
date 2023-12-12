package com.ass.madhwavahini.ui.presentation.navigation.screens.sub_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.domain.repository.SubCategoryRepository
import com.ass.madhwavahini.domain.wrapper.UiStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
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
        UiStateList()
    )

    fun queryChanged(newQuery: String = "") {
        _subCategoryQuery.value = newQuery
    }
}