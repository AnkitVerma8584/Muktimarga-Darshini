package com.ass.madhwavahini.ui_new.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.Navigation
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ass.madhwavahini.ui.presentation.navigation.modal.NavigationFragment
import com.ass.madhwavahini.ui.theme.ShowPreview
import com.ass.madhwavahini.ui.theme.UiModePreviews
import com.ass.madhwavahini.ui.theme.sh12
import com.ass.madhwavahini.ui_new.bottomNavigationItems
import com.ass.madhwavahini.ui_new.home.components.HomePageHeader
import com.ass.madhwavahini.ui_new.home.components.HomePageMessage
import com.ass.madhwavahini.ui_new.home.components.HomePageNavigationCard

@Composable
fun HomePage(
    onNavigate: (route: String) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        bottomBar = { MyBottomNavigation() }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
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
                        onNavigate(NavigationFragment.Category.route)
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

                    }
                }
            }
            sh12()
        }
    }
}


@Composable
fun MyBottomNavigation() {
    val rootNavController = rememberNavController()
    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()

    NavigationBar(
        modifier = Modifier.clip(RoundedCornerShape(24.dp))
    ) {
        bottomNavigationItems.forEach { item ->
            val isSelected: Boolean = item.route == navBackStackEntry?.destination?.route
            NavigationBarItem(
                selected = isSelected,
                label = { Text(text = item.title) },
                onClick = { rootNavController.navigate(item.route) },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) }
            )
        }
    }
}


