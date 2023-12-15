package com.ass.madhwavahini.ui.presentation.ui_new.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.TempleHindu
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.ui.presentation.navigation.modal.BottomNavigationFragments
import com.ass.madhwavahini.ui.presentation.navigation.modal.HomeNavigationFragments
import com.ass.madhwavahini.ui.presentation.ui_new.home.components.HomePageHeader
import com.ass.madhwavahini.ui.presentation.ui_new.home.components.HomePageMessage
import com.ass.madhwavahini.ui.presentation.ui_new.home.components.HomePageNavigationCard
import com.ass.madhwavahini.ui.theme.ShowPreview
import com.ass.madhwavahini.ui.theme.sh12

@Composable
fun HomePage(
    user: User,
    onRootNavigation: (route: String) -> Unit,
    onNavigate: (route: String) -> Unit
) {
    Column {
        HomePageHeader(user.userName)
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
                    onRootNavigation(BottomNavigationFragments.Category.route)
                }
            }
            item {
                HomePageNavigationCard(
                    icon = Icons.Outlined.CalendarMonth,
                    cardTitle = "Panchanga",
                    cardDescription = "The Hindu calendar"
                ) {
                    onNavigate(HomeNavigationFragments.Panchanga.route)
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                HomePageNavigationCard(
                    aspectRatio = 2.16f,
                    icon = Icons.Outlined.TempleHindu,
                    cardTitle = "Aradhna",
                    cardDescription = "The Hindu prayers"
                ) {
                    onNavigate(HomeNavigationFragments.Aradhna.route)
                }
            }
        }
        sh12()
    }
}

@Preview
@Composable
fun HomePagePreview() {
    ShowPreview {
        HomePage(user = User(userName = "Dummy User"), onRootNavigation = {}, onNavigate = {})
    }
}


