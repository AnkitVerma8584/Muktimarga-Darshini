package com.ass.madhwavahini.util.translations

import com.ass.madhwavahini.domain.repository.TranslatorRepository
import com.ass.madhwavahini.domain.wrapper.UiState
import com.ass.madhwavahini.domain.wrapper.UiStateList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest

class MyTranslator(
    scope: CoroutineScope,
    private val translatorRepository: TranslatorRepository
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

    fun setSourceText(text: String) {
        _sourceText.value = text
    }

    fun setDesinationLanguage(language: TranslationLanguages) {
        _destinationLanguage.value = language
    }


}