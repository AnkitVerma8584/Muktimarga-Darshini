package com.ass.madhwavahini.ui.presentation.navigation.destinations.home.panchanga

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.HomePanchanga
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.ShowError
import com.ass.madhwavahini.ui.theme.dimens

@Composable
fun PanchangaPage(
    panchangaViewModel: PanchangaViewModel = hiltViewModel()
) {
    val panchanga by panchangaViewModel.panchangaState.collectAsStateWithLifecycle()

    if (panchanga.isLoading) Loading()

    panchanga.error?.ShowError()

    panchanga.data?.let {
        PanchangaData(panchanga = it)
    }
}

@Composable
private fun PanchangaData(panchanga: HomePanchanga) {
    val config: Configuration = LocalConfiguration.current
    Box(
        modifier = Modifier
            .padding(MaterialTheme.dimens.paddingLarge)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            PanchangaHeader(panchanga = panchanga)
            Divider(color = MaterialTheme.colorScheme.outline)
            PanchangaTitle(title = panchanga.title)
            if (config.screenWidthDp < 800) PanchangaContentCompact(panchanga = panchanga)
            else PanchangaContentExpanded(panchanga = panchanga)
            PanchangaFooter(footer = panchanga.todaySpecial)
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spacerMedium))
        }
    }
}

@Composable
fun PanchangaHeader(
    panchanga: HomePanchanga
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(MaterialTheme.dimens.paddingLarge),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spacerSmall)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                modifier = Modifier.size(24.dp),
                contentDescription = null
            )
            Text(
                text = panchanga.suryodaya, style = MaterialTheme.typography.labelMedium
            )
        }
        Column(
            modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = panchanga.date, style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = panchanga.week, style = MaterialTheme.typography.titleSmall
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.sunset),
                modifier = Modifier.size(24.dp),
                contentDescription = null
            )
            Text(
                text = panchanga.suryasthamaya, style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun PanchangaTitle(title: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = MaterialTheme.dimens.paddingMedium,
                horizontal = MaterialTheme.dimens.paddingLarge
            ),
        text = title,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
}

@Composable
fun PanchangaFooter(footer: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimens.paddingLarge),
        text = "Today's Special",
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimens.paddingLarge),
        text = footer,
        style = MaterialTheme.typography.titleLarge
    )
}
