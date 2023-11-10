package com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals

import com.ass.muktimargadarshini.domain.utils.StringUtil

data class FilesDataState(
    val isLoading: Boolean = false,
    val data: List<FilesData>? = null,
    val error: StringUtil? = null
)