package com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.files.modals

import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.document.modals.FileDocumentText

data class FilesData(
    val homeFile: HomeFile,
    val fileData: List<FileDocumentText>,
)