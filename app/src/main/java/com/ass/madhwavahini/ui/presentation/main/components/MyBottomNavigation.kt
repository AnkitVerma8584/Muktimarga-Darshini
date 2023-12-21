package com.ass.madhwavahini.ui.presentation.main.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.ass.madhwavahini.ui.presentation.navigation.modal.rootNavigationFragmentsLists
import com.ass.madhwavahini.ui.theme.dimens

@Composable
fun MyBottomNavigation(
    navBackStackEntry: NavBackStackEntry?,
    onNavigate: (route: String) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .padding(
                start = MaterialTheme.dimens.paddingLarge,
                bottom = MaterialTheme.dimens.paddingLarge,
                end = MaterialTheme.dimens.paddingLarge
            ).clip(RoundedCornerShape(12.dp))
    ) {
        rootNavigationFragmentsLists.forEach { item ->
            val isSelected: Boolean = item.route == navBackStackEntry?.destination?.route
            NavigationBarItem(
                selected = isSelected,
                label = {
                    Text(
                        text = item.title.asString(),
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.icon,
                        contentDescription = item.title.asString()
                    )
                }
            )
        }
    }
}

