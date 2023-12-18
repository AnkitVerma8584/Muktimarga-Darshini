package com.ass.madhwavahini.ui.presentation.navigation.destinations.home.aradhna

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.domain.repository.AradhnaRepository
import com.ass.madhwavahini.domain.wrapper.UiStateList
import com.ass.madhwavahini.util.print
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AradhnaViewModel @Inject constructor(
    aradhnaRepository: AradhnaRepository
) : ViewModel() {

    private val _aradhnaState = aradhnaRepository.getAradhnas()

    private val _aradhnaQuery = MutableStateFlow("")
    val aradhnaQuery get() = _aradhnaQuery.asStateFlow()

    val aradhnaState = combine(_aradhnaState, _aradhnaQuery) { state, query ->
        state.copy(
            data = state.data?.let {
                it.filter { item -> item.title.contains(query, ignoreCase = true) }
            }
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        UiStateList()
    )

    fun queryChanged(newQuery: String = "") {
        _aradhnaQuery.value = newQuery
    }
}