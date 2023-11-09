package com.ass.muktimargadarshini.util

sealed class UiState{
    data object Idle:UiState()
    data object Loading:UiState()
}
