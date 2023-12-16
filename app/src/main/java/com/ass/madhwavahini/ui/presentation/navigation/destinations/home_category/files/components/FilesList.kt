package com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.files.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.R
import com.ass.madhwavahini.data.Constants
import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.ui.presentation.common.Header
import com.ass.madhwavahini.ui.presentation.common.NoSearchedResults
import com.ass.madhwavahini.ui.presentation.common.SearchedFileHeader
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.document.components.SearchedText
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.files.modals.FilesData

@Composable
fun FilesList(
    searchedContent: List<FilesData>,
    data: List<HomeFile>,
    query: () -> String,
    onFileClicked: (homeFile: HomeFile, query: String, index: Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Adaptive(minSize = Constants.ADAPTIVE_GRID_SIZE),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        searchedContent.forEach { fileData ->
            item(span = { GridItemSpan(maxLineSpan) }) {
                SearchedFileHeader(header = fileData.homeFile.name)
            }
            items(items = fileData.fileData, span = { GridItemSpan(maxLineSpan) }) { text ->
                SearchedText(query = query(), content = text,
                    onClick = {
                        onFileClicked(
                            fileData.homeFile,
                            query(),
                            it
                        )
                    }
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(15.dp))
            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Header(header = stringResource(id = R.string.files))
        }
        if (data.isEmpty())
            item(span = { GridItemSpan(maxLineSpan) }) {
                NoSearchedResults(query = query(), id = R.string.empty_files)
            }
        else {
            items(data, key = { it.uniqueKey }) {
                FileCard(
                    item = it,
                    onFileClicked = onFileClicked, query = query()
                )
            }
        }
    }
}