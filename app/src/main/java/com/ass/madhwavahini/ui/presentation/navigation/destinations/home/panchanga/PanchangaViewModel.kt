package com.ass.madhwavahini.ui.presentation.navigation.destinations.home.panchanga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.domain.repository.PanchangaRepository
import com.ass.madhwavahini.domain.wrapper.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PanchangaViewModel @Inject constructor(
    panchangaRepository: PanchangaRepository
) : ViewModel() {

    private val _panchangaState = panchangaRepository.getPanchanga()

    val panchangaState = _panchangaState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        UiState()
    )

}