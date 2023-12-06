package com.ass.madhwavahini.ui.presentation.navigation.screens.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.ass.madhwavahini.domain.modals.HomeCategory
import com.ass.madhwavahini.ui.presentation.common.getAnnotatedText

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyGridItemScope.CategoryItem(
    data: HomeCategory,
    onClick: (HomeCategory) -> Unit,
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
            Text(
                text = getAnnotatedText(text = data.name.trim(), query = query),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


