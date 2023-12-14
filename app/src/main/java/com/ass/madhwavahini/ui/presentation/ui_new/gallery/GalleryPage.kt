package com.ass.madhwavahini.ui.presentation.ui_new.gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ass.madhwavahini.data.Constants.GALLERY_ADAPTIVE_SIZE
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.ShowError

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryPage(
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val banners by viewModel.bannerState.collectAsStateWithLifecycle()

    if (banners.isLoading) {
        Loading()
        return
    }
    banners.error?.ShowError()
    banners.data?.let { bannersList ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(GALLERY_ADAPTIVE_SIZE),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                items(bannersList, key = { it.id }) { gallery ->
                    AsyncImage(
                        model = gallery.image,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement()
                            .wrapContentHeight()
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}