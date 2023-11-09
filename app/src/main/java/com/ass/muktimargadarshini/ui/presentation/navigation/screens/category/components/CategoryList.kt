package com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ass.muktimargadarshini.R
import com.ass.muktimargadarshini.domain.modals.HomeCategory
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.state.BannerState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.CategoryList(
    category: List<HomeCategory>?,
    banner: BannerState,
    onClick: (HomeCategory) -> Unit
) {
    LazyColumn(modifier = Modifier.weight(1f)) {
        banner.data?.let {
            if (it.isNotEmpty())
                item {
                    Slider(banner = it)
                }
        }
        stickyHeader {
            Text(
                text = stringResource(id = R.string.category),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 16.dp, top = 16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
        category?.let { list ->
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
