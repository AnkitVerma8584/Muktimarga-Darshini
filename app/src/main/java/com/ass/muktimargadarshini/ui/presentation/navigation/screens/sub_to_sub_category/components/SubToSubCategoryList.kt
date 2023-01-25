package com.ass.muktimargadarshini.presentation.ui.navigation.screens.sub_to_sub_category.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.modals.HomeSubToSubCategory
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.common.Loading
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.file_details.components.SearchedText
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.components.FileCard
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubToSubCategoryContent(
    query: String,
    searchedContent: List<FilesData>,
    subToSubCategory: List<HomeSubToSubCategory>?,
    onSubToSubCategoryClick: (HomeSubToSubCategory) -> Unit,
    files: List<HomeFiles>?,
    onFileClicked: (HomeFiles, String, Int) -> Unit,
    onPdfClicked: (homeFiles: HomeFiles) -> Unit
) {
    LazyColumn {
        searchedContent.forEach { fileData ->
            stickyHeader {
                Text(
                    text = fileData.homeFiles.name, modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(12.dp),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            items(fileData.file_data) { text ->
                SearchedText(query = query, content = text, onClick = {
                    onFileClicked(fileData.homeFiles, query, it)
                })
            }
            item {
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
        subToSubCategory?.let { list ->
            items(items = list, key = { it.uniqueKey }) { sub_category ->
                SubToSubCategoryCard(data = sub_category, onClick = onSubToSubCategoryClick)
            }
        } ?: item { Loading() }

        files?.let { list ->
            if (list.isNotEmpty()) {
                stickyHeader {
                    Text(
                        text = "Files",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(start = 16.dp, top = 16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                items(list, key = { it.uniqueKey }) {
                    FileCard(item = it, onFileClicked = onFileClicked, onPdfClicked = onPdfClicked)
                }
            }
        } ?: item { Loading() }
    }
}

