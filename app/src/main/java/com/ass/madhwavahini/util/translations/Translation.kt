package com.ass.madhwavahini.util.translations

import androidx.annotation.StringRes
import com.ass.madhwavahini.R

enum class TranslationLanguages(
    @StringRes val displayName: Int,
    val languageCode: String,
    val preoptions: List<String> = emptyList(),
    val postOptions: List<String> = emptyList()
) {
    ENGLISH(R.string.en, "HK"),
    KANNADA(R.string.kn, "Kannada"),
    TELEGU(R.string.te, "Telugu"),
    TAMIL(R.string.ta, "TamilExtended", preoptions = listOf("TamilNumeralSub")),
    SANSKRIT(R.string.sa, "Devanagari", postOptions = listOf("DevanagariAnusvara"))
}