package com.ass.madhwavahini.util.translation

import com.google.mlkit.nl.translate.TranslateLanguage

enum class TranslationLanguages(val displayName: String, val languageCode: String) {
    ENGLISH("English", TranslateLanguage.ENGLISH),
    KANNADA("ಕನ್ನಡ", TranslateLanguage.KANNADA),
    TELEGU("తెలుగు", TranslateLanguage.TELUGU),
    TAMIL("தமிழ்", TranslateLanguage.TAMIL),
    HINDI("हिंदी", TranslateLanguage.HINDI)
}