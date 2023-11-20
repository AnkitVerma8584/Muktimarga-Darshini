package com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals

import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.domain.wrapper.StringUtil

data class FilesState(
    val isLoading: Boolean = false,
    val data: List<HomeFile>? = null,
    val error: StringUtil? = null
)
