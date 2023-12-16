package com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.document.state_holder

import androidx.lifecycle.SavedStateHandle
import com.ass.madhwavahini.domain.repository.DocumentRepository
import com.ass.madhwavahini.domain.repository.TranslatorRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.domain.wrapper.UiState
import com.ass.madhwavahini.util.translations.MyTranslator
import com.ass.madhwavahini.util.translations.TranslationLanguages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class TextStateHolder(
    scope: CoroutineScope,
    private val filesRepository: DocumentRepository,
    savedStateHandle: SavedStateHandle,
    translatorRepository: TranslatorRepository
) {
    private val myTranslator = MyTranslator(scope, translatorRepository)

    private val _fileState = MutableStateFlow(UiState<File>())
    val fileState = _fileState.asStateFlow()

    private var index = savedStateHandle.get<Int>("index") ?: -1

    private val _fileDataQuery = MutableStateFlow(savedStateHandle["query"] ?: "")
    val fileDataQuery get() = _fileDataQuery.asStateFlow()

    init {
        scope.launch(Dispatchers.IO) {
            fetchDocument(
                savedStateHandle.get<Int>("file_id") ?: 0,
                savedStateHandle.get<String>("file_url") ?: ""
            )
        }
    }

    private suspend fun fetchDocument(homeFileId: Int, homeFileUrl: String) {
        filesRepository.getDocument("file_${homeFileId}.txt", homeFileUrl).collectLatest { result ->
            _fileState.value = result
            result.data?.let { file ->
                readTextFile(file)
            }
        }
    }

    private suspend fun readTextFile(file: File) = withContext(Dispatchers.IO) {
        try {
            val br = BufferedReader(FileReader(file))
            var line: String?
            val textContent = StringBuilder()
            while (br.readLine().also { line = it } != null) {
                textContent.append(line).append("\n")
            }
            br.close()
            myTranslator.setSourceText(textContent.toString())
        } catch (e: Exception) {
            _fileState.update {
                it.copy(
                    isLoading = false,
                    data = null,
                    error = StringUtil.DynamicText(if (e is IOException) "The file is corrupted" else "Unable to display the file")
                )
            }
        }
    }

    val searchedText = combine(fileDataQuery, myTranslator.translatedTextState) { query, list ->
        if (query.length > 2) list.data?.filter { s ->
            s.text.contains(query, ignoreCase = true)
        } ?: emptyList()
        else emptyList()
    }.flowOn(Dispatchers.Default).stateIn(scope, SharingStarted.WhileSubscribed(1000), emptyList())

    fun updateQuery(newQuery: String = "") {
        _fileDataQuery.value = newQuery
    }

    fun getScrollIndex(): Int = index

    fun removeIndexFlag() {
        index = -1
    }

    fun setDestinationLanguage(language: TranslationLanguages) {
        myTranslator.setDestinationLanguage(language)
    }

    fun getTranslationTextState() = myTranslator.translatedTextState

}