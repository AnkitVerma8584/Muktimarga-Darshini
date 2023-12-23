package com.ass.madhwavahini.ui.presentation.navigation.destinations.home.panchanga

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.domain.modals.HomePanchanga
import com.ass.madhwavahini.ui.theme.dimens

@Composable
fun PanchangaContentCompact(panchanga: HomePanchanga) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimens.paddingLarge)
            .padding(bottom = MaterialTheme.dimens.paddingLarge)
            .border(1.dp, MaterialTheme.colorScheme.onSurface)
    ) {
        Row {
            TableItems(tableName = "Ayana", tableValue = panchanga.ayana)
            TableItems(tableName = "Ruthu", tableValue = panchanga.ruthu)
        }
        Row {
            TableItems(tableName = "Masa", tableValue = panchanga.masa)
            TableItems(tableName = "MasaNiyamaka", tableValue = panchanga.masaNiyamaka)
        }
        Row {
            TableItems(tableName = "Paksha", tableValue = panchanga.paksha)
            TableItems(tableName = "Tithi", tableValue = panchanga.tithi)
        }
        Row {
            TableItems(tableName = "Vasara", tableValue = panchanga.vasara)
            TableItems(tableName = "Nakshatra", tableValue = panchanga.nakshatra)
        }
        Row {
            TableItems(tableName = "Yoga", tableValue = panchanga.yoga)
            TableItems(tableName = "Karana", tableValue = panchanga.karana)
        }
    }
}


@Composable
fun PanchangaContentExpanded(panchanga: HomePanchanga) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimens.paddingLarge)
            .padding(bottom = MaterialTheme.dimens.paddingLarge)
            .border(1.dp, MaterialTheme.colorScheme.onSurface)
    ) {
        Row {
            TableItems(tableName = "Ayana", tableValue = panchanga.ayana)
            TableItems(tableName = "Ruthu", tableValue = panchanga.ruthu)
            TableItems(tableName = "Masa", tableValue = panchanga.masa)
        }
        Row {
            TableItems(tableName = "MasaNiyamaka", tableValue = panchanga.masaNiyamaka)
            TableItems(tableName = "Paksha", tableValue = panchanga.paksha)
        }
        Row {
            TableItems(tableName = "Tithi", tableValue = panchanga.tithi)
            TableItems(tableName = "Vasara", tableValue = panchanga.vasara)
            TableItems(tableName = "Nakshatra", tableValue = panchanga.nakshatra)
        }
        Row {
            TableItems(tableName = "Yoga", tableValue = panchanga.yoga)
            TableItems(tableName = "Karana", tableValue = panchanga.karana)
        }
    }
}


@Composable
private fun RowScope.TableItems(tableName: String, tableValue: String) {
    Column(
        modifier = Modifier
            .weight(1f)
            .border((0.5).dp, MaterialTheme.colorScheme.onSurface)
            .padding(MaterialTheme.dimens.paddingMedium)
    ) {
        Text(
            text = tableName,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = tableValue, style = MaterialTheme.typography.bodyLarge
        )
    }
}
