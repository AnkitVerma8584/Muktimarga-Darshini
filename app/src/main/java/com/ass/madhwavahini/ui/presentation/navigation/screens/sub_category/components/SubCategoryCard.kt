package com.ass.madhwavahini.ui.presentation.navigation.screens.sub_category.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ass.madhwavahini.domain.modals.HomeSubCategory
import com.ass.madhwavahini.ui.presentation.common.getAnnotatedText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyGridItemScope.SubCategoryCard(
    data: HomeSubCategory,
    onClick: (HomeSubCategory) -> Unit,
    query: String
) {
    ElevatedCard(modifier = Modifier
        .padding(horizontal = 12.dp, vertical = 8.dp)
        .animateItemPlacement()
        .fillMaxWidth()
        .clickable { onClick(data) }) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(model = data.image),
                contentDescription = null
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = getAnnotatedText(text = data.name, query = query),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                if (data.description.isNotBlank())
                    Text(
                        text = data.description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal
                    )
            }
        }

    }
}
