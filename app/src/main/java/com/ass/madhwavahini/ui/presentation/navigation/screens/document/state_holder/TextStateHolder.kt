package com.ass.madhwavahini.ui.presentation.navigation.screens.document.state_holder

import androidx.lifecycle.SavedStateHandle
import com.ass.madhwavahini.domain.repository.DocumentRepository
import com.ass.madhwavahini.domain.repository.TranslatorRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.domain.wrapper.UiState
import com.ass.madhwavahini.domain.wrapper.UiStateList
import com.ass.madhwavahini.util.translations.TranslationLanguages
import com.google.mlkit.nl.languageid.LanguageIdentification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
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
    private val _sourceText = MutableStateFlow("")
    private val _sourceLanguage = MutableStateFlow(TranslationLanguages.KANNADA)
    private val _destinationLanguage = MutableStateFlow(TranslationLanguages.KANNADA)

    @OptIn(ExperimentalCoroutinesApi::class)
    val translatedTextState =
        combine(_sourceText, _sourceLanguage, _destinationLanguage) { text, source, destination ->
            Triple(text, source, destination)
        }.transformLatest {
            emitAll(translatorRepository.getTranslatedDocument(it.first, it.second, it.third))
        }.flowOn(Dispatchers.Default).stateIn(
            scope, SharingStarted.WhileSubscribed(5000), UiStateList()
        )

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
            _sourceText.value = textContent.toString()
            identifySource(text = textContent.toString())
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


    val searchedText = combine(fileDataQuery, translatedTextState) { query, list ->
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
        _destinationLanguage.value = language
    }

    private fun identifySource(text: String) {
        val languageIdentifier = LanguageIdentification.getClient()
        languageIdentifier.identifyLanguage(text).addOnSuccessListener { languageCode ->
            when (languageCode) {
                "kn" -> {
                    _sourceLanguage.value = TranslationLanguages.KANNADA
                    _destinationLanguage.value = TranslationLanguages.KANNADA
                }

                "ne", "sa", "hi" -> {
                    _sourceLanguage.value = TranslationLanguages.SANSKRIT
                    _destinationLanguage.value = TranslationLanguages.SANSKRIT
                }

                "te" -> {
                    _sourceLanguage.value = TranslationLanguages.TELEGU
                    _destinationLanguage.value = TranslationLanguages.TELEGU
                }

                "ta" -> {
                    _sourceLanguage.value = TranslationLanguages.TAMIL
                    _destinationLanguage.value = TranslationLanguages.TAMIL
                }

                "en" -> {
                    _sourceLanguage.value = TranslationLanguages.ENGLISH
                    _destinationLanguage.value = TranslationLanguages.ENGLISH
                }
            }
        }
    }
}