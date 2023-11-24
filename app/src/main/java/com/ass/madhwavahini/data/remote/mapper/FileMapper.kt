package com.ass.madhwavahini.data.remote.mapper

import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.modals.FileDocumentText
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.FileReader


suspend fun HomeFile.getFileToFilesData(file: File, query: String): FilesData? =
    withContext(Dispatchers.IO) {
        try {
            val br = BufferedReader(FileReader(file))
            var line: String
            val text = mutableListOf<FileDocumentText>()
            val paragraph = StringBuilder()
            while (br.readLine().also { line = it } != null) {
                paragraph.append(line)
            }
            br.close()
            FilesData(this@getFileToFilesData, text.toList())
        } catch (e: Exception) {
            null
        }
    }

suspend fun HomeFile.getFileToFilesData(file: File): FilesData? =
    withContext(Dispatchers.IO) {
        try {
            val br = BufferedReader(FileReader(file))
            var line: String
            val text = mutableListOf<FileDocumentText>()
            while (br.readLine().also { line = it } != null) {
                val index = text.size
                text.add(FileDocumentText(index, line))
            }
            br.close()
            FilesData(this@getFileToFilesData, text.toList())
        } catch (e: Exception) {
            null
        }
    }

