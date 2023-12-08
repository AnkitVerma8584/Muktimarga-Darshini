package com.ass.madhwavahini.util.translation

import androidx.annotation.StringRes
import com.ass.madhwavahini.R
import com.google.mlkit.nl.translate.TranslateLanguage

enum class TranslationLanguages(@StringRes val displayName: Int, val languageCode: String) {
    ENGLISH(R.string.en, TranslateLanguage.ENGLISH),
    KANNADA(R.string.kn, TranslateLanguage.KANNADA),
    TELEGU(R.string.te, TranslateLanguage.TELUGU),
    TAMIL(R.string.ta, TranslateLanguage.TAMIL),
    HINDI(R.string.hi, TranslateLanguage.HINDI)
}