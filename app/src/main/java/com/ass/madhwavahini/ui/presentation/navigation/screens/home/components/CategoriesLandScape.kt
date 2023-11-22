package com.ass.madhwavahini.ui.presentation.navigation.screens.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.HomeCategory
import com.ass.madhwavahini.ui.presentation.common.Header
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.NoSearchedResults
import com.ass.madhwavahini.ui.presentation.common.ShowError
import com.ass.madhwavahini.ui.presentation.navigation.screens.home.state.BannerState
import com.ass.madhwavahini.ui.presentation.navigation.screens.home.state.CategoryState


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoriesLandscape(
    query: String,
    modifier: Modifier = Modifier,
    banners: BannerState,
    categories: CategoryState,
    onClick: (HomeCategory) -> Unit
) {
    Row(modifier = modifier.fillMaxSize()) {

        if (categories.isLoading && banners.isLoading) {
            Loading()
        }
        banners.data?.let {
            Slider(
                banner = it,
                modifier = Modifier.fillMaxHeight()
            )
        }
        LazyColumn(
            modifier = modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
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
                        CategoryItem(data = category, onClick = onClick)
                    }

            }
            item {
                categories.error?.ShowError()
            }
        }
    }
}