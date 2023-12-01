package com.ass.madhwavahini.ui.presentation.navigation.screens.home.components

import android.widget.ImageView
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import coil.load
import com.ass.madhwavahini.data.Constants.BANNER_SLIDE_TIME
import com.ass.madhwavahini.util.print
import com.flaviofaria.kenburnsview.KenBurnsView
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Slider(
    modifier: Modifier,
    banner: List<String>
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { banner.size })

    if (banner.size > 1)
        LaunchedEffect(Unit) {
            while (true) {
                yield()
                delay(BANNER_SLIDE_TIME)
                "BANNER MOVING".print()
                try {
                    pagerState.animateScrollToPage(
                        page = (pagerState.currentPage + 1) % pagerState.pageCount,
                        animationSpec = tween(500)
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    Box(
        modifier = modifier.aspectRatio(16 / 9f),
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalPager(
            state = pagerState, modifier = Modifier.fillMaxSize()
        ) { page ->
            Card(modifier = Modifier
                .graphicsLayer {
                    val pageOffset =
                        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                    lerp(
                        start = 0.75f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .fillMaxWidth()
                .padding(8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                AndroidView(
                    factory = {
                        KenBurnsView(it).also { imageView ->
                            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                            imageView.load(banner[page])
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        if (banner.size > 1)
            HorizontalPagerIndicator(
                pagerState = pagerState,
                pageCount = banner.size,
                modifier = Modifier.padding(22.dp),
                activeColor = MaterialTheme.colorScheme.onPrimaryContainer,
                inactiveColor = MaterialTheme.colorScheme.primaryContainer
            )

    }

}
