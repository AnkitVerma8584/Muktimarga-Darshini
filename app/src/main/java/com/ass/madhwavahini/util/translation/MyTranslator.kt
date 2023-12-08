package com.ass.madhwavahini.util.translation
/*

import android.util.LruCache
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ass.madhwavahini.data.Constants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

class MyTranslator {

    val targetLang = MutableLiveData(TranslationLanguages.KANNADA)
    val sourceText = MutableLiveData<List<String>>()
    val translatedText = MediatorLiveData<ResultOrError>()

    init {
        // Create a translation result or error object.
        val processTranslation =
            OnCompleteListener { task ->
                translatedText.value = ResultOrError(task.result, task.exception)
            }
        translatedText.addSource(sourceText) {
            translateText().addOnCompleteListener(processTranslation)
        }
        val languageObserver = Observer<TranslationLanguages> {
            translateText().addOnCompleteListener(processTranslation)
        }
        translatedText.addSource(targetLang, languageObserver)
    }


    private val cachedTranslators =
        object : LruCache<String, Translator>(Constants.NUM_TRANSLATORS) {
            override fun create(languageCode: String): Translator {
                val options = TranslatorOptions.Builder()
                    .setSourceLanguage(TranslateLanguage.KANNADA)
                    .setTargetLanguage(languageCode)
                    .build()
                return Translation.getClient(options)
            }

            override fun entryRemoved(
                evicted: Boolean,
                key: String,
                oldValue: Translator,
                newValue: Translator?,
            ) {
                oldValue.close()
            }
        }

    private fun translateText(): Task<List<String>> {
        val textToTranslate = sourceText.value
        val destinationLanguage = targetLang.value

        if (destinationLanguage == TranslationLanguages.KANNADA || textToTranslate.isNullOrEmpty()) {
            return Tasks.forResult(textToTranslate)
        }

        return cachedTranslators[destinationLanguage!!.languageCode].downloadModelIfNeeded()
            .continueWithTask { task ->
                val translation = if (task.isSuccessful) {
                    val s = textToTranslate.map { txt ->
                        cachedTranslators[destinationLanguage.languageCode]
                            .translate(txt).result
                    }

                } else {
                    Tasks.forException(
                        task.exception
                            ?: Exception("Unable to download translation model")
                    )
                }

                translation
            }
    }

    inner class ResultOrError(var result: List<String>?, var error: Exception?)

    fun clear() {
        cachedTranslators.evictAll()
    }
}*/
