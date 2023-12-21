package com.ass.madhwavahini.ui.presentation.navigation.destinations.home.aradhna

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ass.madhwavahini.R
import com.ass.madhwavahini.data.Constants
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.NoSearchedResults
import com.ass.madhwavahini.ui.presentation.common.SearchBar
import com.ass.madhwavahini.ui.presentation.common.ShowError
import com.ass.madhwavahini.ui.theme.dimens

@Composable
fun AradhnaPage(
    viewModel: AradhnaViewModel = hiltViewModel()
) {
    val aradhnas by viewModel.aradhnaState.collectAsStateWithLifecycle()
    val query by viewModel.aradhnaQuery.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = MaterialTheme.dimens.paddingLarge,
                end = MaterialTheme.dimens.paddingLarge,
                bottom = MaterialTheme.dimens.paddingLarge
            )
    ) {
        SearchBar(
            hint = stringResource(id = R.string.aradhna_search),
            query = query,
            onSearchQueryChanged = viewModel::queryChanged
        )
        if (aradhnas.isLoading) {
            Loading()
            return
        }

        aradhnas.data?.let { aradhnasList ->
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = Constants.ADAPTIVE_GRID_SIZE),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spacerSmall),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spacerSmall)
            ) {

                if (aradhnasList.isEmpty())
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        NoSearchedResults(query, R.string.empty_aradhna)
                    }
                else
                    items(items = aradhnasList, key = { it.id }) { aradhna ->
                        AradhnaCard(
                            data = aradhna,
                            query = query
                        )
                    }
            }
        }
        aradhnas.error?.ShowError()
    }
}