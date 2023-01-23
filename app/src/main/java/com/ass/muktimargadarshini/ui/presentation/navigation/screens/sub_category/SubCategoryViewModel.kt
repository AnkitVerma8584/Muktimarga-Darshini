package com.ass.muktimargadarshini.presentation.ui.navigation.screens.sub_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.muktimargadarshini.domain.repository.remote.SubCategoryRemoteRepository
import com.ass.muktimargadarshini.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SubCategoryViewModel @Inject constructor(
    private val subCategoryRemoteRepository: SubCategoryRemoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _subCategoryQuery = MutableStateFlow("")
    val subCategoryQuery get() = _subCategoryQuery.asStateFlow()

    private val _state = MutableStateFlow(SubCategoryState())

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

    init {
        viewModelScope.launch(Dispatchers.Default) {
            getSubCategoryData(savedStateHandle.get<Int>("cat_id") ?: 0)
        }
    }

    private suspend fun getSubCategoryData(catId: Int) {
        subCategoryRemoteRepository.getSubCategories(catId).collectLatest {
            when (it) {
                is Resource.Cached -> {
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
                    _state.update { state ->
                        state.copy(isLoading = false, error = null, data = it.result)
                    }
                }
            }
        }
    }

    fun queryChanged(newQuery: String = "") {
        _subCategoryQuery.value = newQuery
    }
}