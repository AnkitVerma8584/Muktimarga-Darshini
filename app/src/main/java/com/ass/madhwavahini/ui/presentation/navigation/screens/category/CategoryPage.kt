package com.ass.madhwavahini.ui.presentation.navigation.screens.category

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.HomeCategory
import com.ass.madhwavahini.ui.presentation.common.SearchBar
import com.ass.madhwavahini.ui.presentation.navigation.screens.category.components.CategoriesLandscape
import com.ass.madhwavahini.ui.presentation.navigation.screens.category.components.CategoriesPortrait

@Composable
fun CategoryPage(
    windowSizeClass: WindowSizeClass,
    viewModel: CategoryViewModel = hiltViewModel(),
    onClick: (HomeCategory) -> Unit
) {
    val banners by viewModel.bannerState.collectAsStateWithLifecycle()
    val categories by viewModel.categoryState.collectAsStateWithLifecycle()

    val query by viewModel.categoryQuery.collectAsState()

    val configuration = LocalConfiguration.current
    val shouldDisplayLandscape =
        (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) &&
                (windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact)

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            hint = stringResource(id = R.string.category_search),
            query = query,
            onSearchQueryChanged = viewModel::queryChanged
        )

        if (shouldDisplayLandscape)
            CategoriesLandscape(
                banners = banners,
                categories = categories,
                onClick = onClick
            )
        else
            CategoriesPortrait(
                banners = banners,
                categories = categories,
                onClick = onClick
            )

    }
}
