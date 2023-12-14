package com.ass.madhwavahini.ui.presentation.ui_new.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.domain.repository.HomeRepository
import com.ass.madhwavahini.domain.wrapper.UiStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    homeRepository: HomeRepository
) : ViewModel() {

    private val _bannerState =
        homeRepository.getBannerState().flowOn(Dispatchers.IO)

    val bannerState = _bannerState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        UiStateList()
    )

}