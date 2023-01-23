package com.ass.muktimargadarshini.util

import android.util.Log

fun Any?.print(tag: String = "TAGS") {
    Log.e(tag, this.toString())
}

fun String.isInValidFile(): Boolean =
    !this.endsWith(".pdf") && !this.endsWith(".txt")

fun String.getExtension(): String = this.split(".").last()

fun String.isReadable(): Boolean = this.endsWith(".txt")