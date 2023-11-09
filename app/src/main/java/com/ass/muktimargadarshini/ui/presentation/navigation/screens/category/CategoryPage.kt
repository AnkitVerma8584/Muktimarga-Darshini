package com.ass.muktimargadarshini.ui.presentation.navigation.screens.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ass.muktimargadarshini.R
import com.ass.muktimargadarshini.domain.modals.HomeCategory
import com.ass.muktimargadarshini.ui.presentation.common.Loading
import com.ass.muktimargadarshini.ui.presentation.common.SearchBar
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.components.CategoryItem
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.components.CategoryList
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.components.Slider
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.state.BannerState
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.state.CategoryState

@Composable
fun CategoryPage(
    windowSizeClass: WindowSizeClass,
    viewModel: CategoryViewModel = hiltViewModel(),
    onClick: (HomeCategory) -> Unit
) {
    val banners by viewModel.bannerState.collectAsStateWithLifecycle()
    val categories by viewModel.categoryState.collectAsStateWithLifecycle()

    if (windowSizeClass.widthSizeClass < WindowWidthSizeClass.Medium)
        CategoryScreenPortrait(
            viewModel = viewModel,
            banners = banners,
            categories = categories,
            onClick = onClick
        )
    else
        CategoryScreenLandscape(
            viewModel = viewModel,
            banners = banners,
            categories = categories,
            onClick = onClick
        )
}

@Composable
private fun CategoryScreenPortrait(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel,
    banners: BannerState,
    categories: CategoryState,
    onClick: (HomeCategory) -> Unit
) {
    val query by viewModel.categoryQuery.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        SearchBar(
            hint = stringResource(id = R.string.category_search),
            query = query,
            onSearchQueryChanged = viewModel::queryChanged
        )
        if (categories.isLoading) {
            Loading()
        }
        CategoryList(
            category = categories.data,
            banner = banners,
            onClick = onClick
        )
        categories.error?.let {
            Text(
                text = it.asString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .padding(5.dp),
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}

@Composable
private fun CategoryScreenLandscape(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel,
    banners: BannerState,
    categories: CategoryState,
    onClick: (HomeCategory) -> Unit
) {
    val query by viewModel.categoryQuery.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            hint = stringResource(id = R.string.category_search),
            query = query,
            onSearchQueryChanged = viewModel::queryChanged
        )
        Row(modifier = modifier.fillMaxSize()) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                banners.data?.let {
                    if (it.isNotEmpty())
                        Slider(banner = it)
                }
            }
            LazyColumn(
                modifier = modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                if (categories.isLoading) {
                    item{ Loading() }
                }
                categories.data?.let { list ->
                    run {
                        if (list.isEmpty())
                            item {
                                Text(
                                    text = "No results found!",
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                        else
                            items(items = list, key = { it.id }) { category ->
                                CategoryItem(data = category, onClick = onClick)
                            }
                    }
                }
            }
        }
    }
}