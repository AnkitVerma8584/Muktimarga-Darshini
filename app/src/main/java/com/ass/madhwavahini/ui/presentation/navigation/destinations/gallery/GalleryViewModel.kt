package com.ass.madhwavahini.ui.presentation.navigation.destinations.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.domain.repository.GalleryRepository
import com.ass.madhwavahini.domain.wrapper.UiStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    galleryRepository: GalleryRepository
) : ViewModel() {

    private val _galleryState =
        galleryRepository.getGalleryState().flowOn(Dispatchers.IO)

    val galleryState = _galleryState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        UiStateList()
    )

}