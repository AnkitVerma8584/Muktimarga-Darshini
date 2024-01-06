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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.ui.presentation.navigation.modal.HomeNavigationFragments
import com.ass.madhwavahini.ui.presentation.navigation.modal.RootNavigationFragments
import com.ass.madhwavahini.ui.theme.ShowPreview
import com.ass.madhwavahini.ui.theme.dimens
import com.ass.madhwavahini.util.sh12

@Composable
fun HomePage(
    homeViewModel: HomeViewModel = hiltViewModel(),
    user: User,
    onRootNavigation: (route: String) -> Unit,
    onNavigate: (route: String) -> Unit
) {
    val quotesState by homeViewModel.homeState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimens.paddingLarge)
    ) {
        HomePageHeader(user.userName)
        sh12.invoke()
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = MaterialTheme.dimens.homeGridSize),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.paddingLarge),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.paddingLarge)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                HomePageMessage(quotesState)
            }
            item {
                HomePageNavigationCard(
                    icon = Icons.Outlined.Category,
                    cardTitle = stringResource(id = R.string.files)
                ) {
                    onRootNavigation(RootNavigationFragments.Category.route)
                }
            }
            item {
                HomePageNavigationCard(
                    icon = Icons.Outlined.CalendarMonth,
                    cardTitle = "Panchanga"
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


