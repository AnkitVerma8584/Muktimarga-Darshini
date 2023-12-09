package com.ass.madhwavahini.ui.presentation.navigation.screens.document.text

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.domain.repository.DocumentRepository
import com.ass.madhwavahini.ui.presentation.navigation.screens.document.state_holder.AudioStateHolder
import com.ass.madhwavahini.ui.presentation.navigation.screens.document.state_holder.TextStateHolder
import com.ass.madhwavahini.util.player.MyPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TextDocumentViewModel @Inject constructor(
    filesRepository: DocumentRepository,
    myPlayer: MyPlayer,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val textStateHolder: TextStateHolder =
        TextStateHolder(viewModelScope, filesRepository, savedStateHandle)

    val audioStateHolder: AudioStateHolder =
        AudioStateHolder(viewModelScope, myPlayer, savedStateHandle)


    override fun onCleared() {
        super.onCleared()
        audioStateHolder.clearPlayer()
    }
}
