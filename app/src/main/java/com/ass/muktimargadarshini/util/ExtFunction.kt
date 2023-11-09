package com.ass.muktimargadarshini.util

import android.util.Log

fun Any?.print(tag: String = "TAGS") {
    Log.e(tag, this.toString())
}

fun String.isInValidFile(): Boolean =
    !this.endsWith(".pdf", ignoreCase = true) && !this.endsWith(".txt", ignoreCase = true)
