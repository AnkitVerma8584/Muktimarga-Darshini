package com.ass.muktimargadarshini.util

import android.util.Log
import com.ass.muktimargadarshini.domain.utils.StringUtil
import okio.IOException

fun Any?.print(tag: String = "TAGS") {
    Log.e(tag, this.toString())
}

fun String.isInValidFile(): Boolean =
    !this.endsWith(".pdf", ignoreCase = true) && !this.endsWith(".txt", ignoreCase = true)


fun Exception.getError(): StringUtil {
    return StringUtil.DynamicText(
        if (this is IOException) "Please check your internet connection" else {
            this.localizedMessage ?: "Some server error occurred"
        }
    )
}