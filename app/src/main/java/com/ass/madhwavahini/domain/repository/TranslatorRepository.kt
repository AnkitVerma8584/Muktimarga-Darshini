package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.wrapper.UiStateList
import com.ass.madhwavahini.ui.presentation.navigation.screens.document.modals.FileDocumentText
import com.ass.madhwavahini.util.translations.TranslationLanguages
import kotlinx.coroutines.flow.Flow

interface TranslatorRepository {
    fun getTranslatedDocument(
        text: String,
        source: TranslationLanguages,
        destination: TranslationLanguages
    ): Flow<UiStateList<FileDocumentText>>
}