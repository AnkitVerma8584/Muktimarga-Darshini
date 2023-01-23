package com.ass.muktimargadarshini.ui.presentation.navigation.screens.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ass.muktimargadarshini.domain.modals.HomeCategory
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.components.CategoryList
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.common.Loading
import com.ass.muktimargadarshini.presentation.ui.navigation.screens.common.SearchBar
import com.ass.muktimargadarshini.R
@Composable
fun CategoryPage(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = hiltViewModel(),
    onClick: (HomeCategory) -> Unit
) {
    val banners by viewModel.bannerState.collectAsStateWithLifecycle()
    val categories by viewModel.categoryState.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        SearchBar(
            hint = stringResource(id = R.string.category_search),
            query = viewModel.categoryQuery.collectAsStateWithLifecycle().value,
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