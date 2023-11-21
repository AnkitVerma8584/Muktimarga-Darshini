package com.ass.madhwavahini.ui.presentation.navigation.screens.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ass.madhwavahini.domain.modals.HomeCategory
import com.ass.madhwavahini.ui.presentation.common.Header
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.NoSearchedResults
import com.ass.madhwavahini.ui.presentation.common.ShowError
import com.ass.madhwavahini.ui.presentation.navigation.screens.home.state.BannerState
import com.ass.madhwavahini.ui.presentation.navigation.screens.home.state.CategoryState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoriesPortrait(
    banners: BannerState,
    categories: CategoryState,
    onClick: (HomeCategory) -> Unit
) {
    // If both are loading
    if (banners.isLoading && categories.isLoading) {
        Loading()
    } else
        LazyColumn {

            //Banner sliders
            banners.data?.let {
                item {
                    Slider(banner = it, modifier = Modifier.fillMaxWidth())
                }
            }

            //Categories
            categories.data?.let { list ->
                stickyHeader {
                    Header(header = "Categories")
                }
                if (list.isEmpty())
                    item {
                        NoSearchedResults()
                    }
                else
                    items(items = list, key = { it.id }) { category ->
                        CategoryItem(data = category, onClick = onClick)
                    }
            }
            //Categories error
            item {
                categories.error?.ShowError()
            }
        }

}