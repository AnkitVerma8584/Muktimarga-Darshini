package com.ass.madhwavahini.ui.presentation.navigation.destinations.gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.ShowError
import com.ass.madhwavahini.ui.theme.dimens

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
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.dimens.paddingLarge),
            columns = StaggeredGridCells.Adaptive(MaterialTheme.dimens.galleryGridSize),
            verticalItemSpacing = MaterialTheme.dimens.spacerExtraSmall,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spacerExtraSmall),
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
            }
        )
    }
}