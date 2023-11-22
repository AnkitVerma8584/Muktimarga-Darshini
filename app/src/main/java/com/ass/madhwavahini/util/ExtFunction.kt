package com.ass.madhwavahini.util

import android.util.Log
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.wrapper.StringUtil
import okio.IOException

fun Any?.print(tag: String = "TAG") {
    Log.e(tag, this.toString())
}

fun String.isInValidFile(): Boolean =
    !this.endsWith(".pdf", ignoreCase = true) && !this.endsWith(".txt", ignoreCase = true)

fun Exception.getError(): StringUtil {
    return StringUtil.StringResource(
        if (this is IOException)
            R.string.check_internet else
            R.string.server_error
    )
}

fun Long.formatTime(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}
