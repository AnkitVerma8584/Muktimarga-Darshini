package com.ass.muktimargadarshini.presentation.ui.navigation.screens.file_details.modals

import com.ass.muktimargadarshini.domain.utils.StringUtil

data class FileDataState(
    val isLoading: Boolean = false,
    val error: StringUtil? = null
)
