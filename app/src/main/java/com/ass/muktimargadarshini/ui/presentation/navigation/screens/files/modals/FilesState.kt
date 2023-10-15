package com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals

import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.utils.StringUtil

data class FilesState(
    val isLoading: Boolean = false,
    val data: List<HomeFiles>? = null,
    val error: StringUtil? = null
)
