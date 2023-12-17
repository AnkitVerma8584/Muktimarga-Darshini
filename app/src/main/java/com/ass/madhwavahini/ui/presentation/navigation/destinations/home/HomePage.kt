package com.ass.madhwavahini.ui.presentation.navigation.destinations.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Category
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.data.Constants
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.ui.presentation.navigation.modal.RootNavigationFragments
import com.ass.madhwavahini.ui.presentation.navigation.modal.HomeNavigationFragments
import com.ass.madhwavahini.ui.theme.ShowPreview
import com.ass.madhwavahini.ui.theme.sh12
import com.ass.madhwavahini.R
@Composable
fun HomePage(
    user: User,
    onRootNavigation: (route: String) -> Unit,
    onNavigate: (route: String) -> Unit
) {
    Column(modifier= Modifier
        .fillMaxSize()
        .padding(16.dp))  {
        HomePageHeader(user.userName)
        sh12.invoke()
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = Constants.HOME_ADAPTIVE_SIZE),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                HomePageMessage()
            }
            item {
                HomePageNavigationCard(
                    icon = Icons.Outlined.Category,
                    cardTitle = stringResource(id = R.string.category),
                    cardDescription = "Explore the vast "
                ) {
                    onRootNavigation(RootNavigationFragments.Category.route)
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
            item(span = { GridItemSpan(2) }) {
                HomePageAradhnaCard {
                    onNavigate(HomeNavigationFragments.Aradhna.route)
                }
            }
        }
        sh12.invoke()
    }
}

@Preview
@Composable
fun HomePagePreview() {
    ShowPreview {
        HomePage(user = User(userName = "Dummy User"), onRootNavigation = {}, onNavigate = {})
    }
}


