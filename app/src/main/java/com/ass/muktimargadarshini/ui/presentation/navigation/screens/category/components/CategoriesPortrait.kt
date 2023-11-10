package com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ass.muktimargadarshini.domain.modals.HomeCategory
import com.ass.muktimargadarshini.ui.presentation.common.Header
import com.ass.muktimargadarshini.ui.presentation.common.Loading
import com.ass.muktimargadarshini.ui.presentation.common.NoSearchedResults
import com.ass.muktimargadarshini.ui.presentation.common.ShowErrorText
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.state.BannerState
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.state.CategoryState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.CategoriesPortrait(
    banners: BannerState,
    categories: CategoryState,
    onClick: (HomeCategory) -> Unit
) {
    LazyColumn(modifier = Modifier.weight(1f)) {
        if (banners.isLoading) {
            item { Loading() }
        }
        banners.data?.let {
            item {
                Slider(banner = it, modifier = Modifier.fillMaxWidth())
            }
        }
        stickyHeader {
            Header(header = "Categories")
        }
        if (categories.isLoading) {
            item { Loading() }
        }
        categories.data?.let { list ->
            if (list.isEmpty())
                item {
                    NoSearchedResults()
                }
            else
                items(items = list, key = { it.id }) { category ->
                    CategoryItem(data = category, onClick = onClick)
                }
        }
        item {
            categories.error?.ShowErrorText()
        }
    }

}