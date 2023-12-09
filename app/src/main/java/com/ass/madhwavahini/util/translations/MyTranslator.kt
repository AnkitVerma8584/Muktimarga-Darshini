package com.ass.madhwavahini.util.translations

import com.ass.madhwavahini.domain.repository.TranslatorRepository
import com.ass.madhwavahini.domain.wrapper.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class MyTranslator(
    private val scope: CoroutineScope,
    private val translatorRepository: TranslatorRepository
) {
    private val _sourceText = MutableStateFlow("")

    private val _sourceLanguage = MutableStateFlow(TranslationLanguages.KANNADA)
    private val _destinationLanguage = MutableStateFlow(TranslationLanguages.KANNADA)

    val translatedText =
        combine(_sourceText, _sourceLanguage, _destinationLanguage) { text, source, destination ->
            translatorRepository.getTranslatedDocument(text, source, destination)
        }.flowOn(Dispatchers.Default).stateIn(
            scope, SharingStarted.WhileSubscribed(5000), UiState<String>()
        )


    fun translate() {

    }
}