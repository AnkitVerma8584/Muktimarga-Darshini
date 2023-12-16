package com.ass.madhwavahini.ui.presentation.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.ass.madhwavahini.R
import com.ass.madhwavahini.ui.presentation.navigation.modal.rootNavigationFragmentsLists

@Composable
fun MyNavigationRail(
    shouldShowBuyButton: Boolean,
    onBuyClick: () -> Unit,
    navBackStackEntry: NavBackStackEntry?,
    onNavigate: (route: String) -> Unit
) {
    NavigationRail(
        header = {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .padding(8.dp)
            )
            if (shouldShowBuyButton)
                PurchasePackButton(onBuyClick = onBuyClick)
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(rootNavigationFragmentsLists) { item ->
                val isSelected: Boolean = item.route == navBackStackEntry?.destination?.route
                NavigationRailItem(
                    selected = isSelected, onClick = {
                        onNavigate(item.route)
                    }, label = {
                        Text(
                            text = item.title.asString(),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }, icon = {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.icon,
                            contentDescription = item.title.asString()
                        )
                    })
            }
        }
    }
}