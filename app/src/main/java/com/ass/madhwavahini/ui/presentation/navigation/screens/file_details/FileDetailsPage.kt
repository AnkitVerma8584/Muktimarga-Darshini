package com.ass.madhwavahini.ui.presentation.navigation.screens.file_details

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.selection.SelectionContainer
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ass.madhwavahini.R
import com.ass.madhwavahini.data.Constants.MINIMUM_SEARCH_CHAR
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.SearchBar
import com.ass.madhwavahini.ui.presentation.common.ShowError
import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.components.AudioToggleButton
import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.components.BottomMusicBar
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
    var scale by rememberSaveable { mutableFloatStateOf(16f) }

    var isDisplayingAudio by rememberSaveable {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = query,
            onSearchQueryChanged = viewModel::updateQuery,
            hint = "Search for any text...",
            minimumLetter = MINIMUM_SEARCH_CHAR
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, _ ->
                    if ((scale * zoom) in 11.0f..60.0f) scale *= zoom
                }
            }) {

            if (state.isLoading)
                Loading()

            state.error?.ShowError()

            DocumentContent(
                viewModel = viewModel,
                scale = scale,
                query = query,
                scrollIndex = viewModel.getScrollIndex(),
                onRemoveIndex = viewModel::removeIndexFlag
            )
            if (viewModel.hasAudioFile)
                AudioToggleButton(isDisplayingAudio = isDisplayingAudio) {
                    isDisplayingAudio = !isDisplayingAudio
                }

        }
        if (viewModel.hasAudioFile)
            BottomMusicBar(viewModel, isDisplayingAudio)

    }
}

@Composable
private fun BoxScope.DocumentContent(
    viewModel: FileDetailsViewModel,
    query: String,
    scale: Float,
    scrollIndex: Int,
    onRemoveIndex: () -> Unit
) {
    val text by viewModel.text.collectAsState()
    val searchedText by viewModel.searchedText.collectAsState()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val resources = LocalContext.current.resources

    SelectionContainer {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            // if (query.length > 2) {
            item {
                Text(
                    text = resources.getQuantityString(
                        R.plurals.numberOfSearchResults,
                        searchedText.size,
                        searchedText.size
                    ),
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
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
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            //}
            items(text, key = { it.index }) { item ->
                if (item.text.isNotBlank())
                    DocumentText(query = query, text = item.text, scale = scale)
                else Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    val shouldShowButton by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    ScrollToTopButton(shouldShowButton = shouldShowButton, onClick = {
        coroutineScope.launch {
            listState.animateScrollToItem(index = 0)
        }
    })

     val totalItems by remember { derivedStateOf { listState.layoutInfo.totalItemsCount } }

     if (query.length > 2 && scrollIndex == -1)
         LaunchedEffect(key1 = query) {
             listState.animateScrollToItem(0)
         }
     if (text.isNotEmpty() && searchedText.isNotEmpty() && (searchedText.size + scrollIndex) < totalItems && scrollIndex != -1) {
         LaunchedEffect(Unit) {
             listState.animateScrollToItem(searchedText.size + scrollIndex)
             onRemoveIndex()
         }
     }

}
