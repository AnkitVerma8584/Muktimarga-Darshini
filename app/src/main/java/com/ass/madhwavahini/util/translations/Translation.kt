package com.ass.madhwavahini.util.translations

import androidx.annotation.StringRes
import com.ass.madhwavahini.R

enum class TranslationLanguages(
    @StringRes val displayName: Int,
    val languageCode: String
) {
    ENGLISH(R.string.en, "HK"),
    KANNADA(R.string.kn, "Kannada"),
    TELEGU(R.string.te, "Telugu"),
    TAMIL(R.string.ta, "TamilExtended"),
    SANSKRIT(R.string.sa, "Devanagari")
}