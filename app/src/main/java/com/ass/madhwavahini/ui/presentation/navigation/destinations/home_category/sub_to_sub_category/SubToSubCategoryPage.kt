package com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.sub_to_sub_category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.HomeFile
import com.ass.madhwavahini.domain.modals.HomeSubToSubCategory
import com.ass.madhwavahini.ui.presentation.common.SearchBar
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.sub_to_sub_category.components.SubToSubCategoryContent
import com.ass.madhwavahini.ui.theme.dimens

@Composable
fun SubToSubCategoryPage(
    viewModel: SubToSubCategoryViewModel = hiltViewModel(),
    onSubToSubCategoryClick: (HomeSubToSubCategory) -> Unit,
    onFileClicked: (HomeFile, String, Int) -> Unit
) {
    val subToSubCategories by viewModel.subToSubCategoryState.collectAsState()
    val files by viewModel.fileState.collectAsState()
    val searchedData by viewModel.searchedFilesData.collectAsState()
    val query by viewModel.query.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = MaterialTheme.dimens.paddingLarge)) {
        SearchBar(
            hint = stringResource(id = R.string.sub_to_sub_cat_search),
            query = query,
            onSearchQueryChanged = viewModel::queryChanged
        )
        SubToSubCategoryContent(
            query,
            searchedData,
            subToSubCategories,
            onSubToSubCategoryClick,
            files,
            onFileClicked
        )
    }
}