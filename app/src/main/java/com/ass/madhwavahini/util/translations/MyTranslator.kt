package com.ass.madhwavahini.util.translations

import androidx.collection.LruCache

object MyTranslatorCache {

    private val documentCache = LruCache<TranslationLanguages, String>(4 * 1024 * 1024)

    fun addDocument(key: TranslationLanguages, value: String) {
        documentCache.put(key, value)
    }

    fun getDocument(key: TranslationLanguages) = documentCache.get(key)

    fun clearCache() {
        documentCache.evictAll()
    }

}