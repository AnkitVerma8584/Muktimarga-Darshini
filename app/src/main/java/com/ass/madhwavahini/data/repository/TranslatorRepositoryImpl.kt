package com.ass.madhwavahini.data.repository

import com.ass.madhwavahini.data.remote.apis.TranslateApi
import com.ass.madhwavahini.domain.repository.TranslatorRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.domain.wrapper.UiStateList
import com.ass.madhwavahini.ui.presentation.navigation.screens.document.modals.FileDocumentText
import com.ass.madhwavahini.util.getError
import com.ass.madhwavahini.util.translations.TranslationLanguages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TranslatorRepositoryImpl(
    private val translateApi: TranslateApi
) : TranslatorRepository {
    override fun getTranslatedDocument(
        text: String,
        source: TranslationLanguages,
        destination: TranslationLanguages
    ): Flow<UiStateList<FileDocumentText>> = flow {
        var state = UiStateList<FileDocumentText>(isLoading = true)
        emit(state)
        if (text.isBlank() || source == destination) {
            val list = text.split("\n").mapIndexed { index, s ->
                FileDocumentText("${destination.name}_$index", index, s)
            }
            emit(UiStateList(data = list))
            return@flow
        }

        try {
            val result =
                translateApi.translateText(
                    source = source.languageCode,
                    target = destination.languageCode,
                    text = text
                )
            state = if (result.isSuccessful && result.body() != null) {
                val data = result.body()!!
                val list = data.split("\n").mapIndexed { index, s ->
                    FileDocumentText("${destination.name}_$index", index, s)
                }
                //TODO try caching the translation
                state.copy(isLoading = false, data = list)
            } else {
                state.copy(
                    isLoading = false,
                    error = StringUtil.DynamicText("Unable to translate data.")
                )
            }
        } catch (e: Exception) {
            state = state.copy(
                isLoading = false,
                error = e.getError()
            )
        }
        emit(state)
    }
}