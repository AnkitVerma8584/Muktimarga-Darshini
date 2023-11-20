package com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals

import com.ass.madhwavahini.domain.wrapper.StringUtil

data class FilesDataState(
    val isLoading: Boolean = false,
    val data: List<FilesData>? = null,
    val error: StringUtil? = null
)