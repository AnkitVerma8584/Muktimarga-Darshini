package com.ass.madhwavahini.data.repository

import com.ass.madhwavahini.data.remote.apis.TranslateApi
import com.ass.madhwavahini.domain.repository.TranslatorRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.domain.wrapper.UiState
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
    ): Flow<UiState<String>> = flow {
        var state = UiState<String>(isLoading = true)
        emit(state)

        //TODO TRY FETCHING FROM CACHE
        /*if (localFiles.isNotEmpty()) {
            state = state.copy(isLoading = false, data = localFiles)
            emit(state)
        }*/
        try {
            val result =
                translateApi.translateText(
                    source = source.languageCode,
                    target = destination.languageCode,
                    text = text
                )
            state = if (result.isSuccessful && result.body() != null) {
                val data = result.body()!!
                //TODO try caching the translation
                state.copy(isLoading = false, data = data)
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
        } finally {
            emit(state)
        }
    }
}