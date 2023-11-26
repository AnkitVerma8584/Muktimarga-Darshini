package com.ass.madhwavahini.ui.presentation.navigation.screens.sub_to_sub_category.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.domain.modals.HomeSubToSubCategory
import com.ass.madhwavahini.domain.wrapper.UiStateList
import com.ass.madhwavahini.ui.presentation.common.Header
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.NoSearchedResults
import com.ass.madhwavahini.ui.presentation.common.SearchedFileHeader
import com.ass.madhwavahini.ui.presentation.common.ShowError
import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.components.SearchedText
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.components.FileCard
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesData

@OptIn(ExperimentalFoundationApi::class)
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
    subToSubCategory.error?.let {
        if (files.data == null && !files.isLoading) {
            it.ShowError()
            return
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        searchedContent.forEach { fileData ->
            stickyHeader {
                SearchedFileHeader(header = fileData.homeFile.name)
            }
            items(fileData.fileData) { text ->
                SearchedText(query = query, content = text, onClick = {
                    onFileClicked(fileData.homeFile, query, it)
                })
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
                SubToSubCategoryCard(data = subCategory, onClick = onSubToSubCategoryClick)
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
                FileCard(item = it, onFileClicked = onFileClicked)
            }
        }
    }
}

