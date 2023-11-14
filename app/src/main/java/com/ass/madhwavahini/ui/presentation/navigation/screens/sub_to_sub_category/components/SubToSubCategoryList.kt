package com.ass.madhwavahini.ui.presentation.navigation.screens.sub_to_sub_category.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.domain.modals.HomeSubToSubCategory
import com.ass.madhwavahini.ui.presentation.common.Header
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.NoSearchedResults
import com.ass.madhwavahini.ui.presentation.common.SearchedFileHeader
import com.ass.madhwavahini.ui.presentation.common.ShowErrorText
import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.components.SearchedText
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.components.FileCard
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesData
import com.ass.madhwavahini.ui.presentation.navigation.screens.files.modals.FilesState
import com.ass.madhwavahini.ui.presentation.navigation.screens.sub_to_sub_category.modal.SubToSubCategoryState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubToSubCategoryContent(
    query: String,
    searchedContent: List<FilesData>,
    subToSubCategory: SubToSubCategoryState,
    onSubToSubCategoryClick: (HomeSubToSubCategory) -> Unit,
    files: FilesState,
    onFileClicked: (HomeFile, String, Int) -> Unit
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
                    query = query,
                    content = text,
                    onClick = {
                        onFileClicked(fileData.homeFile, query, it)
                    })
            }
            item {
                Spacer(modifier = Modifier.height(15.dp))
            }
        }

        if (subToSubCategory.isLoading || subToSubCategory.data != null || (files.data == null && !files.isLoading))
            stickyHeader {
                Header("Sub-To-Sub-Categories")
            }

        if (subToSubCategory.isLoading) {
            item {
                Loading()
            }
        }


        if (files.data == null && !files.isLoading)
            item { subToSubCategory.error?.ShowErrorText() }


        subToSubCategory.data?.let { list ->
            if (list.isEmpty())
                item {
                    NoSearchedResults()
                }
            else
                items(items = list, key = { it.uniqueKey }) { subCategory ->
                    SubToSubCategoryCard(data = subCategory, onClick = onSubToSubCategoryClick)
                }
        }

        if (files.isLoading) {
            item {
                Loading()
            }
        }
        files.data?.let { list ->
            stickyHeader {
                Header("Files")
            }
            if (list.isEmpty())
                item {
                    NoSearchedResults()
                }
            else
                items(list, key = { it.uniqueKey }) {
                    FileCard(item = it, onFileClicked = onFileClicked)
                }
        }
    }
}

