package com.ass.madhwavahini.ui.presentation.navigation.screens.files

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ass.madhwavahini.data.Constants.MINIMUM_SEARCH_CHAR
import com.ass.madhwavahini.domain.modals.HomeFiles
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.SearchBar
import com.ass.madhwavahini.ui.presentation.common.ShowErrorText
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.components.FilesList

@Composable
fun FilePage(
    viewModel: FilesViewModel = hiltViewModel(),
    onFileClicked: (homeFiles: HomeFiles, query: String, index: Int) -> Unit,
    onPdfClicked: (homeFiles: HomeFiles) -> Unit
) {
    val files by viewModel.fileState.collectAsState()
    val searchedData by viewModel.fileData.collectAsState()
    val query by viewModel.query.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            hint = "Search for any files",
            query = query,
            onSearchQueryChanged = viewModel::queryChanged,
            minimumLetter = MINIMUM_SEARCH_CHAR
        )
        if (files.isLoading)
            Loading()
        files.error?.ShowErrorText()
        files.data?.let {
            FilesList(
                searchedContent = searchedData,
                data = it,
                query,
                onFileClicked = onFileClicked,
                onPdfClicked = onPdfClicked
            )
        }
    }
}
