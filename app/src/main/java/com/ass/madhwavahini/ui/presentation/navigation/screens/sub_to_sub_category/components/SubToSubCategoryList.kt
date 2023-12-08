package com.ass.madhwavahini.ui.presentation.navigation.screens.sub_to_sub_category.components

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
import com.ass.madhwavahini.domain.modals.HomeSubToSubCategory
import com.ass.madhwavahini.domain.wrapper.UiStateList
import com.ass.madhwavahini.ui.presentation.common.Header
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.NoSearchedResults
import com.ass.madhwavahini.ui.presentation.common.SearchedFileHeader
import com.ass.madhwavahini.ui.presentation.common.ShowError
import com.ass.madhwavahini.ui.presentation.navigation.screens.document.components.SearchedText
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.components.FileCard
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesData

@Composable
fun SubToSubCategoryContent(
    query: String,
    searchedContent: List<FilesData>,
    subToSubCategory: UiStateList<HomeSubToSubCategory>,
    onSubToSubCategoryClick: (HomeSubToSubCategory) -> Unit,
    files: UiStateList<HomeFile>,
    onFileClicked: (HomeFile, String, Int) -> Unit
) {
    // If both are loading show a single progressbar
    if (subToSubCategory.isLoading && files.isLoading) {
        Loading()
        return
    }
    // If there are no files then only show subToSubCategory error
    files.error?.let {
        if (subToSubCategory.data == null && !subToSubCategory.isLoading) {
            it.ShowError()
            return
        }
    }

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Adaptive(minSize = Constants.ADAPTIVE_GRID_SIZE)
    ) {
        searchedContent.forEach { fileData ->
            item(span = { GridItemSpan(maxLineSpan) }) {
                SearchedFileHeader(header = fileData.homeFile.name)
            }
            items(items = fileData.fileData, span = { GridItemSpan(maxLineSpan) }) { text ->
                SearchedText(query = query, content = text,
                    onClick = { onFileClicked(fileData.homeFile, query, it) }
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(15.dp))
            }
        }

        subToSubCategory.data?.let { list ->
            item(span = { GridItemSpan(maxLineSpan) }) {
                Header(stringResource(id = R.string.sub_to_sub_cat))
            }
            if (list.isEmpty()) item {
                NoSearchedResults(query, R.string.empty_sub_to_sub_categories)
            }
            else items(items = list, key = { it.uniqueKey }) { subCategory ->
                SubToSubCategoryCard(
                    data = subCategory,
                    onClick = onSubToSubCategoryClick,
                    query = query
                )
            }
        }

        files.data?.let { list ->
            item(span = { GridItemSpan(maxLineSpan) }) {
                Header(stringResource(id = R.string.files))
            }
            if (list.isEmpty()) item(span = { GridItemSpan(maxLineSpan) }) {
                NoSearchedResults(query, R.string.empty_files)
            }
            else items(list, key = { it.uniqueKey }) {
                FileCard(item = it, onFileClicked = onFileClicked, query = query)
            }
        }
    }

    /* LazyColumn(
         modifier = Modifier.fillMaxSize()
     ) {

         searchedContent.forEach { fileData ->
             stickyHeader {
                 SearchedFileHeader(header = fileData.homeFile.name)
             }
             items(fileData.fileData) { text ->
                 SearchedText(query = query, content = text,
                     onClick = { onFileClicked(fileData.homeFile, query, it) }
                 )
             }
             item {
                 Spacer(modifier = Modifier.height(15.dp))
             }
         }

         subToSubCategory.data?.let { list ->
             stickyHeader {
                 Header(stringResource(id = R.string.sub_to_sub_cat))
             }
             if (list.isEmpty()) item {
                 NoSearchedResults(query, R.string.empty_sub_to_sub_categories)
             }
             else items(items = list, key = { it.uniqueKey }) { subCategory ->
                 SubToSubCategoryCard(
                     data = subCategory,
                     onClick = onSubToSubCategoryClick,
                     query = query
                 )
             }
         }

         files.data?.let { list ->
             stickyHeader {
                 Header(stringResource(id = R.string.files))
             }
             if (list.isEmpty()) item {
                 NoSearchedResults(query, R.string.empty_files)
             }
             else items(list, key = { it.uniqueKey }) {
                 FileCard(item = it, onFileClicked = onFileClicked, query = query)
             }
         }
     }*/
}

