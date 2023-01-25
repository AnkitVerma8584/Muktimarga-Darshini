package com.ass.muktimargadarshini.ui.presentation.navigation.screens.sub_to_sub_category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ass.muktimargadarshini.data.Constants.MINIMUM_SEARCH_CHAR
import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.domain.modals.HomeSubToSubCategory
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.common.SearchBar
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.sub_to_sub_category.components.SubToSubCategoryContent
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.sub_to_sub_category.SubToSubCategoryViewModel

@Composable
fun SubToSubCategoryPage(
    viewModel: SubToSubCategoryViewModel = hiltViewModel(),
    onSubToSubCategoryClick: (HomeSubToSubCategory) -> Unit,
    onFileClicked: (HomeFiles, String, Int) -> Unit,
    onPdfClicked: (homeFiles: HomeFiles) -> Unit
) {
    val subToSubCategories by viewModel.subToSubCategoryState.collectAsState()
    val files by viewModel.fileState.collectAsState()
    val searchedData by viewModel.fileData.collectAsState()
    val query by viewModel.query.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            hint = "Search for any sub categories or files",
            query = query,
            onSearchQueryChanged = viewModel::queryChanged,
            minimumLetter = MINIMUM_SEARCH_CHAR
        )
        SubToSubCategoryContent(
            query,
            searchedData,
            subToSubCategories.data,
            onSubToSubCategoryClick,
            files.data,
            onFileClicked,
            onPdfClicked
        )
    }
}


