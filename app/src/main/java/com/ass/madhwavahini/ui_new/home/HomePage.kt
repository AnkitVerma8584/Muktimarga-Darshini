package com.ass.madhwavahini.ui_new.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.TempleHindu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.ass.madhwavahini.ui.presentation.navigation.modal.BottomNavigationFragments
import com.ass.madhwavahini.ui.presentation.navigation.modal.bottomNavigationFragmentsList
import com.ass.madhwavahini.ui.theme.sh12
import com.ass.madhwavahini.ui_new.home.components.HomePageHeader
import com.ass.madhwavahini.ui_new.home.components.HomePageMessage
import com.ass.madhwavahini.ui_new.home.components.HomePageNavigationCard

@Composable
fun HomePage(
    onNavigate: (route: String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        HomePageHeader()
        sh12()
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                HomePageMessage()
            }
            item {
                HomePageNavigationCard(
                    icon = Icons.Outlined.Category,
                    cardTitle = "Category",
                    cardDescription = "Explore the vast "
                ) {
                    onNavigate(BottomNavigationFragments.Category.route)
                }
            }
            item {
                HomePageNavigationCard(
                    icon = Icons.Outlined.CalendarMonth,
                    cardTitle = "Panchanga",
                    cardDescription = "The Hindu calendar"
                ) {

                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                HomePageNavigationCard(
                    aspectRatio = 2.16f,
                    icon = Icons.Outlined.TempleHindu,
                    cardTitle = "Aradhna",
                    cardDescription = "The Hindu prayers"
                ) {
                    onNavigate(BottomNavigationFragments.Aradhna.route)
                }
            }
        }
        sh12()
    }
}

@Composable
fun MyBottomNavigation(
    navBackStackEntry: NavBackStackEntry?,
    onNavigate: (route: String) -> Unit
) {
    NavigationBar(
        modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(12.dp))
    ) {
        bottomNavigationFragmentsList.forEach { item ->
            val isSelected: Boolean = item.route == navBackStackEntry?.destination?.route
            NavigationBarItem(
                selected = isSelected,
                label = { Text(text = item.title.asString()) },
                onClick = { onNavigate(item.route) },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title.asString()) }
            )
        }
    }
}


