package com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.modals

import com.ass.madhwavahini.domain.wrapper.StringUtil
import java.io.File

data class DocumentState(
    val isLoading: Boolean = false,
    val error: StringUtil? = null,
    val data: File? = null
)
