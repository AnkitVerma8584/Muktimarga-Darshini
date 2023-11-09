package com.ass.muktimargadarshini.ui.presentation.navigation.screens.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.muktimargadarshini.domain.repository.HomeRepository
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.state.BannerState
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.state.CategoryState
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
    homeRepository: HomeRepository
) : ViewModel() {
    private val _categoryState =
        homeRepository.getCategoryState().flowOn(IO)

    private val _bannerState =
        homeRepository.getBannerState().flowOn(IO)

    val bannerState = _bannerState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        BannerState()
    )

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
        CategoryState()
    )

    fun queryChanged(newQuery: String = "") {
        _categoryQuery.value = newQuery
    }
}