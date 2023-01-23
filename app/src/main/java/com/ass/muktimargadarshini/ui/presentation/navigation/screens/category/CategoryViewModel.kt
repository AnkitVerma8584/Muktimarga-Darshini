package com.ass.muktimargadarshini.ui.presentation.navigation.screens.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.muktimargadarshini.domain.repository.remote.HomeRemoteRepository
import com.ass.muktimargadarshini.domain.utils.Resource
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.state.BannerState
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.state.CategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val homeRemoteRepository: HomeRemoteRepository
) : ViewModel() {

    private val _categoryState = MutableStateFlow(CategoryState())

    private val _bannerState = MutableStateFlow(BannerState())
    val bannerState = _bannerState.asStateFlow()

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

    init {
        viewModelScope.launch(Default) {
            launch {
                getCategoryData()
            }
            launch {
                getBannerData()
            }
        }
    }

    private suspend fun getCategoryData() {
        homeRemoteRepository.getCategories().collectLatest {
            when (it) {
                is Resource.Cached -> {
                    _categoryState.update { state ->
                        state.copy(isLoading = false, data = it.result)
                    }
                }
                is Resource.Failure -> {
                    _categoryState.update { state ->
                        state.copy(isLoading = false, error = it.error)
                    }
                }
                Resource.Loading -> {
                    _categoryState.update { state ->
                        state.copy(isLoading = true, error = null, data = null)
                    }
                }
                is Resource.Success -> {
                    _categoryState.update { state ->
                        state.copy(isLoading = false, error = null, data = it.result)
                    }
                }
            }
        }
    }

    private suspend fun getBannerData() {
        homeRemoteRepository.getBanners().collectLatest {
            when (it) {
                is Resource.Cached -> {
                    _bannerState.update { state ->
                        state.copy(isLoading = false, data = it.result)
                    }
                }
                is Resource.Failure -> {
                    _bannerState.update { state ->
                        state.copy(isLoading = false, error = it.error)
                    }
                }
                Resource.Loading -> {
                    _bannerState.update { state ->
                        state.copy(isLoading = true, error = null, data = null)
                    }
                }
                is Resource.Success -> {
                    _bannerState.update { state ->
                        state.copy(isLoading = false, error = null, data = it.result)
                    }
                }
            }
        }
    }

    fun queryChanged(newQuery: String = "") {
        _categoryQuery.value = newQuery
    }

}