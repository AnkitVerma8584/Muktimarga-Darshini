package com.ass.madhwavahini.ui_new.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun GalleryPage() {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(200.dp),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(imageUrls) { photo ->
                AsyncImage(
                    model = photo,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

val imageUrls = listOf(
    "https://source.unsplash.com/1200x900/?travel",
    "https://source.unsplash.com/1600x1800/?nature",
    "https://source.unsplash.com/1800x1800/?technology",
    "https://source.unsplash.com/1600x1200/?food",
    "https://source.unsplash.com/1800x1600/?architecture",
    "https://source.unsplash.com/1600x2000/?fashion",
    "https://source.unsplash.com/1600x1800/?music",
    "https://source.unsplash.com/1800x1600/?sports",
    "https://source.unsplash.com/1800x1800/?business",
    "https://source.unsplash.com/2000x1600/?animals",
    "https://source.unsplash.com/2200x1800/?city",
    "https://source.unsplash.com/2300x1600/?water",
    "https://source.unsplash.com/2400x1800/?mountain",
    "https://source.unsplash.com/2000x1800/?night",
    "https://source.unsplash.com/1800x2000/?summer",
    "https://source.unsplash.com/2000x1800/?winter",
    "https://source.unsplash.com/2000x2400/?portrait",
    "https://source.unsplash.com/2000x1900/?vintage",
    "https://source.unsplash.com/2400x1800/?ocean",
    "https://source.unsplash.com/2500x1800/?sunset",
    "https://source.unsplash.com/2400x1800/?forest",
    "https://source.unsplash.com/2200x1800/?beach",
    "https://source.unsplash.com/2400x1800/?urban",
    "https://source.unsplash.com/2500x1800/?holiday",
    "https://source.unsplash.com/2400x1800/?art",
    "https://source.unsplash.com/2200x1800/?colorful",
    "https://source.unsplash.com/2400x1800/?abstract",
    "https://source.unsplash.com/2400x1800/?architecture",
    "https://source.unsplash.com/1800x1800/?technology",
    "https://source.unsplash.com/2000x1800/?business",
    "https://source.unsplash.com/2000x1800/?arts",
    "https://source.unsplash.com/2200x1800/?cars",
    "https://source.unsplash.com/2400x1800/?fitness",
    "https://source.unsplash.com/2200x1800/?science",
    "https://source.unsplash.com/2100x1800/?people",

    )