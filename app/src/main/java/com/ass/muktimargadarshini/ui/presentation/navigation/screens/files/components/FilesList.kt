package com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.ui.presentation.common.Header
import com.ass.muktimargadarshini.ui.presentation.common.NoSearchedResults
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.file_details.components.SearchedText
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilesList(
    searchedContent: List<FilesData>,
    data: List<HomeFiles>,
    query: String,
    onFileClicked: (homeFiles: HomeFiles, query: String, index: Int) -> Unit,
    onPdfClicked: (homeFiles: HomeFiles) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        searchedContent.forEach { fileData ->
            stickyHeader {
                Header(header = fileData.homeFiles.name)
            }
            items(fileData.fileData) { text ->
                SearchedText(
                    query = query, content = text, onClick = {
                        onFileClicked(fileData.homeFiles, query, it)
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
                FileCard(item = it, onFileClicked = onFileClicked, onPdfClicked = onPdfClicked)
            }
        }
    }
}