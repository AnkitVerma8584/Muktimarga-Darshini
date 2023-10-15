package com.ass.muktimargadarshini.ui.presentation.navigation.screens.sub_category.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ass.muktimargadarshini.domain.modals.HomeSubCategory

@Composable
fun SubCategoryList(
    data: List<HomeSubCategory> = emptyList(),
    onClick: (HomeSubCategory) -> Unit
) {
    Text(
        text = "Sub-Categories",
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp),
        style = MaterialTheme.typography.titleMedium
    )
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 300.dp)) {
        if (data.isEmpty())
            item {
                Text(
                    text = "No results found!",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(
                        horizontal = 16.dp
                    )
                )
            }
        else
            items(items = data, key = { it.id }) { subCategory ->
                SubCategoryCard(data = subCategory, onClick = onClick)
            }
    }
    /*LazyColumn {
        stickyHeader {
            Text(
                text = "Sub-Categories",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(start = 16.dp, top = 16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
        if (data.isEmpty())
            item {
                Text(
                    text = "No results found!",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(
                        horizontal = 16.dp
                    )
                )
            }
        else
            items(items = data, key = { it.id }) { sub_category ->
                SubCategoryCard(data = sub_category, onClick = onClick)
            }
    }*/
}
