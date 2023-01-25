package com.ass.muktimargadarshini.ui.presentation

import androidx.lifecycle.ViewModel
import com.ass.muktimargadarshini.domain.repository.remote.DataRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRemoteRepository: DataRemoteRepository
) : ViewModel() {
    init {

    }
}