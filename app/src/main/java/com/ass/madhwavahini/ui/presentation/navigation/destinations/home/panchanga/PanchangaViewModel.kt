package com.ass.madhwavahini.ui.presentation.navigation.destinations.home.panchanga


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.domain.repository.PanchangaRepository
import com.ass.madhwavahini.domain.wrapper.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PanchangaViewModel @Inject constructor(
    panchangaRepository: PanchangaRepository
) : ViewModel() {

    private val selectedDate = MutableStateFlow(Calendar.getInstance().time)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _panchangaState = selectedDate.flatMapLatest { date->
        panchangaRepository.getPanchanga(SimpleDateFormat("yyyy-MM-dd",
            Locale.getDefault()).format(date))
    }

    val panchangaState = _panchangaState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        UiState()
    )

    fun setDate(date: Date){
        selectedDate.value = date
    }

    private var _shouldOpenDatePicker  = MutableStateFlow(false)
    val shouldOpenDatePicker = _shouldOpenDatePicker.asStateFlow()


    fun setDatePickerState(state:Boolean){
        _shouldOpenDatePicker.value = state
    }


}