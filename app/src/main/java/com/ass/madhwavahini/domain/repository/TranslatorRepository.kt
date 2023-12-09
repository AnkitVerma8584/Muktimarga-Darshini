package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.wrapper.UiState
import com.ass.madhwavahini.util.translations.TranslationLanguages
import kotlinx.coroutines.flow.Flow

interface TranslatorRepository {
    fun getTranslatedDocument(
        text: String,
        source: TranslationLanguages,
        destination: TranslationLanguages
    ): Flow<UiState<String>>
}