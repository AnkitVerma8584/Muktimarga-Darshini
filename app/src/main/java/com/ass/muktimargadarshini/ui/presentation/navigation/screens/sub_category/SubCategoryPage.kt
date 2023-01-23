package com.ass.muktimargadarshini.presentation.ui.navigation.screens.sub_category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ass.muktimargadarshini.domain.modals.HomeSubCategory
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.common.SearchBar
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.sub_category.components.SubCategoryList

@Composable
fun SubCategoryPage(
    onSubCategoryClicked: (HomeSubCategory) -> Unit,
    viewModel: SubCategoryViewModel = hiltViewModel()
) {
    val subCategories by viewModel.subCategoryState.collectAsState()
    val query by viewModel.subCategoryQuery.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            hint = "Search for any sub-category",
            query = query,
            onSearchQueryChanged = viewModel::queryChanged
        )
        subCategories.data?.let {
            SubCategoryList(
                data = it,
                onClick = onSubCategoryClicked
            )
        } ?: CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}