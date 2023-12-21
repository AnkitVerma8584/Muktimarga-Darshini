package com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ass.madhwavahini.R
import com.ass.madhwavahini.data.Constants
import com.ass.madhwavahini.domain.modals.HomeCategory
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.NoSearchedResults
import com.ass.madhwavahini.ui.presentation.common.SearchBar
import com.ass.madhwavahini.ui.presentation.common.ShowError
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.category.components.CategoryItem
import com.ass.madhwavahini.ui.theme.dimens

@Composable
fun CategoryPage(
    viewModel: CategoryViewModel = hiltViewModel(),
    onClick: (HomeCategory) -> Unit = {}
) {
    val categories by viewModel.categoryState.collectAsStateWithLifecycle()
    val query by viewModel.categoryQuery.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimens.paddingLarge)
    ) {
        SearchBar(
            hint = stringResource(id = R.string.category_search),
            query = query,
            onSearchQueryChanged = viewModel::queryChanged
        )
        if (categories.isLoading) {
            Loading()
            return
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = MaterialTheme.dimens.categoryGridSize),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.paddingLarge),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.paddingLarge)
        ) {
            categories.data?.let { list ->
                if (list.isEmpty())
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        NoSearchedResults(query = query, id = R.string.empty_categories)
                    }
                else
                    items(items = list, key = { it.id }) { category ->
                        CategoryItem(data = category, onClick = onClick, query = query)
                    }
            }
            //Categories error
            item(span = { GridItemSpan(maxLineSpan) }) {
                categories.error?.ShowError()
            }
        }

    }
}
