package com.ass.madhwavahini.ui.presentation.navigation.screens.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoriesPortrait(
    query: String,
    banners: UiStateList<String>,
    categories: UiStateList<HomeCategory>,
    onClick: (HomeCategory) -> Unit
) {
    // If both are loading
    if (banners.isLoading && categories.isLoading) {
        Loading()
    } else
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = ADAPTIVE_GRID_SIZE)) {
            banners.data?.let {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Slider(banner = it, modifier = Modifier.fillMaxWidth())
                }
            }
            categories.data?.let { list ->
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Header(header = stringResource(id = R.string.category))
                }
                if (list.isEmpty())
                    item(span = { GridItemSpan(maxLineSpan) })  {
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
    /*LazyColumn {
        //Banner sliders
        banners.data?.let {
            item {
                Slider(banner = it, modifier = Modifier.fillMaxWidth())
            }
        }

        //Categories
        categories.data?.let { list ->
            stickyHeader {
                Header(header = stringResource(id = R.string.category))
            }
            if (list.isEmpty())
                item {
                    NoSearchedResults(query = query, id = R.string.empty_categories)
                }
            else
                items(items = list, key = { it.id }) { category ->
                    CategoryItem(data = category, onClick = onClick, query = query)
                }
        }
        //Categories error
        item {
            categories.error?.ShowError()
        }
    }*/

}