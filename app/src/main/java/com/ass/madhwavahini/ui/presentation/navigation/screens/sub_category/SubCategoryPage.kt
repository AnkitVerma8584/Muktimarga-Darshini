package com.ass.madhwavahini.ui.presentation.navigation.screens.sub_category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.HomeSubCategory
import com.ass.madhwavahini.ui.presentation.common.Header
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.NoSearchedResults
import com.ass.madhwavahini.ui.presentation.common.SearchBar
import com.ass.madhwavahini.ui.presentation.common.ShowError
import com.ass.madhwavahini.ui.presentation.navigation.screens.sub_category.components.SubCategoryCard

@Composable
fun SubCategoryPage(
    onSubCategoryClicked: (HomeSubCategory) -> Unit,
    viewModel: SubCategoryViewModel = hiltViewModel()
) {
    val subCategories by viewModel.subCategoryState.collectAsState()
    val query by viewModel.subCategoryQuery.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            hint = stringResource(id = R.string.sub_cat_search),
            query = query,
            onSearchQueryChanged = viewModel::queryChanged
        )

        if (subCategories.isLoading)
            Loading()

        subCategories.data?.let { subCategoriesList ->
            Header(header = stringResource(id = R.string.sub_cat))
            LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 300.dp)) {
                if (subCategoriesList.isEmpty())
                    item {
                        NoSearchedResults(query, R.string.empty_subcategories)
                    }
                else
                    items(items = subCategoriesList, key = { it.id }) { subCategory ->
                        SubCategoryCard(
                            data = subCategory,
                            onClick = onSubCategoryClicked,
                            query = query
                        )
                    }
            }
        }
        subCategories.error?.ShowError()
    }
}