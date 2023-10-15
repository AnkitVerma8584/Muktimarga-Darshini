package com.ass.muktimargadarshini.ui.presentation.navigation.screens.pdf

import com.ass.muktimargadarshini.domain.utils.StringUtil
import java.io.File

data class PdfState(
    val isLoading: Boolean = false,
    val error: StringUtil? = null,
    val file: File? = null
)