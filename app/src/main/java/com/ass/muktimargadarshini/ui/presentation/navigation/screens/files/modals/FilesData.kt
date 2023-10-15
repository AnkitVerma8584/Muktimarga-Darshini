package com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals

import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.file_details.modals.FileDocumentText

data class FilesData(
    val homeFiles: HomeFiles,
    val fileData: List<FileDocumentText>,
)