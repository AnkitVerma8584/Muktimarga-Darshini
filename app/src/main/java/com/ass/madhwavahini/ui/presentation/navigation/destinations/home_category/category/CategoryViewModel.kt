package com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.domain.repository.CategoryRepository
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
class CategoryViewModel @Inject constructor(
    categoryRepository: CategoryRepository
) : ViewModel() {

    private val _categoryState =
        categoryRepository.getCategoryState().flowOn(IO)


    private val _categoryQuery = MutableStateFlow("")
    val categoryQuery get() = _categoryQuery.asStateFlow()

    val categoryState = combine(_categoryState, categoryQuery) { state, query ->
        state.copy(
            data = state.data?.let {
                it.filter { homeCategory -> homeCategory.name.contains(query, ignoreCase = true) }
            }
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        UiStateList()
    )

    fun queryChanged(newQuery: String = "") {
        _categoryQuery.value = newQuery
    }
}