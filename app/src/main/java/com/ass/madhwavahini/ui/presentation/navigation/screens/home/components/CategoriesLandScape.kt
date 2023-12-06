package com.ass.madhwavahini.ui.presentation.navigation.screens.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ass.madhwavahini.R
import com.ass.madhwavahini.data.Constants.ADAPTIVE_GRID_SIZE
import com.ass.madhwavahini.domain.modals.HomeCategory
import com.ass.madhwavahini.domain.wrapper.UiStateList
import com.ass.madhwavahini.ui.presentation.common.Header
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.NoSearchedResults
import com.ass.madhwavahini.ui.presentation.common.ShowError


@Composable
fun CategoriesLandscape(
    query: String,
    modifier: Modifier = Modifier,
    banners: UiStateList<String>,
    categories: UiStateList<HomeCategory>,
    onClick: (HomeCategory) -> Unit
) {
    Row(modifier = modifier.fillMaxSize()) {

        if (categories.isLoading && banners.isLoading) {
            Loading()
        }
        banners.data?.let {
            Slider(
                banner = it,
                modifier = Modifier.weight(1f)
            )
        }
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxHeight()
                .weight(2f),
            columns = GridCells.Adaptive(minSize = ADAPTIVE_GRID_SIZE)
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
            item {
                categories.error?.ShowError()
            }
        }
    }
}