package com.ass.madhwavahini.ui.presentation.navigation.screens.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.ass.madhwavahini.ui.presentation.common.Header
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.NoSearchedResults
import com.ass.madhwavahini.ui.presentation.common.SearchBar
import com.ass.madhwavahini.ui.presentation.common.ShowError
import com.ass.madhwavahini.ui.presentation.navigation.screens.category.components.CategoryItem

@Composable
fun CategoryPage(
    viewModel: CategoryViewModel = hiltViewModel(),
    onClick: (HomeCategory) -> Unit = {}
) {
    val categories by viewModel.categoryState.collectAsStateWithLifecycle()
    val query by viewModel.categoryQuery.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
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
            columns = GridCells.Adaptive(minSize = Constants.GALLERY_ADAPTIVE_SIZE),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            categories.data?.let { list ->
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Header(header = stringResource(id = R.string.category))
                }
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
