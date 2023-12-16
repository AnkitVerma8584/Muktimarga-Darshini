package com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.category.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        .animateItemPlacement()
        .clickable { onClick(data) }) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillBounds,
            painter = rememberAsyncImagePainter(model = data.image),
            contentDescription = null
        )
        Text(
            text = getAnnotatedText(text = data.name.trim(), query = query),
            modifier = Modifier.padding(horizontal = 12.dp),
            minLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }

}


