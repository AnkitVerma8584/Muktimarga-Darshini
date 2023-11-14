package com.ass.madhwavahini.ui.presentation.navigation.screens.file_details

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ass.madhwavahini.data.Constants.MINIMUM_SEARCH_CHAR
import com.ass.madhwavahini.ui.presentation.common.SearchBar
import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.components.AudioToggleButton
import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.components.DocumentText
import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.components.ScrollToTopButton
import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.components.SearchedText
import kotlinx.coroutines.launch

@Composable
fun FileDetailsPage(
    viewModel: FileDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.fileState.collectAsState()
    val query by viewModel.fileDataQuery.collectAsState()
    var scale by remember { mutableFloatStateOf(16f) }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = query,
            onSearchQueryChanged = viewModel::updateQuery,
            hint = "Search for any text...",
            minimumLetter = MINIMUM_SEARCH_CHAR
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, _ ->
                    if ((scale * zoom) in 11.0f..60.0f) scale *= zoom
                }
            }) {
            if (state.isLoading) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            state.error?.let {
                Text(
                    text = it.asString(),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } ?: DocumentContent(
                viewModel = viewModel,
                scale = scale,
                query = query,
                scrollIndex = viewModel.getScrollIndex()
            )


            var isDisplayingAudio by remember {
                mutableStateOf(false)
            }
            AudioToggleButton(isDisplayingAudio = isDisplayingAudio) {
                isDisplayingAudio = !isDisplayingAudio
            }
        }
    }
}

@Composable
private fun BoxScope.DocumentContent(
    viewModel: FileDetailsViewModel,
    query: String, scale: Float, scrollIndex: Int
) {
    val text by viewModel.text.collectAsState()
    val searchedText by viewModel.searchedText.collectAsState()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    if (text.isEmpty())
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    else {
        SelectionContainer {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                if (query.length > 2) {
                    item {
                        Text(
                            text = "Found ${searchedText.size} results.",
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    if (searchedText.isNotEmpty()) {
                        items(searchedText) { content ->
                            SearchedText(
                                query = query,
                                content = content,
                                scale = scale,
                                onClick = {
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(searchedText.size + it)
                                    }
                                })
                        }
                    }
                }
                items(text) { item ->
                    DocumentText(query = query, text = item, scale = scale)
                }
            }
        }

        ScrollToTopButton(listState = listState, coroutineScope = coroutineScope)

        val totalItems by remember { derivedStateOf { listState.layoutInfo.totalItemsCount } }
        if (text.isNotEmpty() && searchedText.isNotEmpty() && (searchedText.size + scrollIndex) < totalItems && scrollIndex != -1) {
            LaunchedEffect(Unit) {
                listState.animateScrollToItem(searchedText.size + scrollIndex)
                viewModel.removeIndexFlag()
            }
        }


    }
}
