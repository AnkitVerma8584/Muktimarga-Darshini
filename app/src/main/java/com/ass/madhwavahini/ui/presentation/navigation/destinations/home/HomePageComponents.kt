package com.ass.madhwavahini.ui.presentation.navigation.destinations.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.TempleHindu
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.domain.modals.HomeQuotes
import com.ass.madhwavahini.domain.wrapper.UiState
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.theme.ShowPreview
import com.ass.madhwavahini.ui.theme.UiModePreviews
import com.ass.madhwavahini.ui.theme.dimens
import com.ass.madhwavahini.util.print
import com.ass.madhwavahini.util.sh12

@Composable
fun HomePageHeader(userName: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Good morning",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        IconButton(onClick = {
            //TODO some implementation
        }) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications"
            )
        }
    }
}


@Composable
fun HomePageMessage(homeState: UiState<HomeQuotes>) {
    LaunchedEffect(homeState) {
        homeState.print()
    }
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
    ) {
        if (homeState.isLoading) {
            //TODO SHOW SHIMMER TEXT
            Loading()
        }
        homeState.data?.let {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = it.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerSmall))
                Text(
                    text = it.description,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun HomePageNavigationCard(
    icon: ImageVector,
    cardTitle: String,
    cardDescription: String,
    onCardClick: () -> Unit
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onSurface
        ), modifier = Modifier
            .wrapContentHeight()
            .aspectRatio(1f)
            .clickable(onClick = onCardClick)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.dimens.paddingLarge),
                verticalArrangement = Arrangement.Bottom
            ) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(MaterialTheme.dimens.paddingLarge),
                    imageVector = icon,
                    contentDescription = null
                )
                sh12.invoke()
                Text(
                    text = cardTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = cardDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                modifier = Modifier
                    .fillMaxSize(0.5f)
                    .offset(10.dp, (-10).dp)
                    .align(Alignment.TopEnd)
                    .graphicsLayer {
                        rotationZ = -45f
                        alpha = 0.02f
                    }, imageVector = icon, contentDescription = null
            )
        }
    }
}

@Composable
fun HomePageAradhnaCard(
    onCardClick: () -> Unit
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onSurface
        ), modifier = Modifier
            .aspectRatio(2.16f)
            .clickable(onClick = onCardClick)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.dimens.paddingLarge),
                verticalArrangement = Arrangement.Bottom
            ) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(MaterialTheme.dimens.paddingLarge),
                    imageVector = Icons.Outlined.TempleHindu,
                    contentDescription = null
                )
                sh12.invoke()
                Text(
                    text = "Aradhna",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Hindu prayers and shlokas",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }
            Icon(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .aspectRatio(1.2f)
                    .align(Alignment.CenterEnd)
                    .graphicsLayer {
                        alpha = 0.02f
                    }, imageVector = Icons.Outlined.TempleHindu, contentDescription = null
            )
        }
    }
}

@UiModePreviews
@Composable
fun HomePageNavigationCardPreview() {
    ShowPreview {
        HomePageAradhnaCard {}
    }
}
