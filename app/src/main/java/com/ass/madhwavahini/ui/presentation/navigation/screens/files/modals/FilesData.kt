package com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals

import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.ui.presentation.navigation.screens.document.modals.FileDocumentText

data class FilesData(
    val homeFile: HomeFile,
    val fileData: List<FileDocumentText>,
)