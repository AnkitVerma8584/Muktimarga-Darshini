package com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals

import com.ass.madhwavahini.domain.modals.HomeFiles
import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.modals.FileDocumentText

data class FilesData(
    val homeFiles: HomeFiles,
    val fileData: List<FileDocumentText>,
)