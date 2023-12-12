package com.ass.madhwavahini.ui.presentation.main_content

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.R


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationGraphicsApi::class)
@Composable
fun MyAppBar(
    title: String,
    hamburgerIconClicked: () -> Unit,
    navigationBackClicked: () -> Unit,
    isNavigationFragment: Boolean,
    isPaidCustomer: Boolean,
    onBuyClicked: () -> Unit
) {
    val image: AnimatedImageVector =
        AnimatedImageVector.animatedVectorResource(R.drawable.hamburger_back)

    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ), title = {
        Text(
            text = title.trim(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }, navigationIcon = {
        Icon(
            painter = rememberAnimatedVectorPainter(image, isNavigationFragment),
            contentDescription = if (isNavigationFragment) "Open Drawer" else "Navigate Back",
            modifier = Modifier
                .clickable(onClick = if (isNavigationFragment) hamburgerIconClicked else navigationBackClicked)
                .padding(8.dp)
        )
    }, actions = {
        if (!isPaidCustomer) IconButton(onClick = onBuyClicked) {
            Text(
                text = "Buy",
                style = MaterialTheme.typography.titleSmall
            )
        }
    })
}