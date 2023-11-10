package com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.components

import android.widget.ImageView
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import coil.load
import com.flaviofaria.kenburnsview.KenBurnsView
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Slider(
    modifier: Modifier,
    banner: List<String>
) {
    val pagerState = rememberPagerState(pageCount = banner.size, initialPage = 0)
    if (banner.size > 1)
        LaunchedEffect(Unit) {
            while (true) {
                yield()
                delay(5000)
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
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
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
                val customView = KenBurnsView(LocalContext.current).also { imageView ->
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    imageView.load(banner[page])
                }
                AndroidView(
                    factory = { customView },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        if (banner.size > 1)
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier.padding(22.dp),
                activeColor = MaterialTheme.colorScheme.onPrimaryContainer,
                inactiveColor = MaterialTheme.colorScheme.primaryContainer
            )
    }

}
