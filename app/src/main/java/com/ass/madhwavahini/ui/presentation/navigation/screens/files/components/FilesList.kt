package com.ass.madhwavahini.ui.presentation.navigation.screens.files.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.ui.presentation.common.Header
import com.ass.madhwavahini.ui.presentation.common.NoSearchedResults
import com.ass.madhwavahini.ui.presentation.common.SearchedFileHeader
import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.components.SearchedText
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilesList(
    searchedContent: List<FilesData>,
    data: List<HomeFile>,
    query: String,
    onFileClicked: (homeFile: HomeFile, query: String, index: Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        searchedContent.forEach { fileData ->
            stickyHeader {
                SearchedFileHeader(header = fileData.homeFile.name)
            }
            items(fileData.fileData) { text ->
                SearchedText(
                    query = query, content = text, onClick = {
                        onFileClicked(fileData.homeFile, query, it)
                    })
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        stickyHeader {
            Header(header = "Files")
        }
        if (data.isEmpty())
            item {
                NoSearchedResults()
            }
        else {
            items(data, key = { it.id }) {
                FileCard(
                    item = it,
                    onFileClicked = onFileClicked
                )
            }
        }
    }
}