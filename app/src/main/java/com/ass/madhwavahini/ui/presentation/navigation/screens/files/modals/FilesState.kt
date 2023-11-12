package com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals

import com.ass.madhwavahini.domain.modals.HomeFiles
import com.ass.madhwavahini.domain.utils.StringUtil

data class FilesState(
    val isLoading: Boolean = false,
    val data: List<HomeFiles>? = null,
    val error: StringUtil? = null
)
