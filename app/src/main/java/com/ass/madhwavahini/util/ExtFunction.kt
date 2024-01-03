package com.ass.madhwavahini.util

import android.util.Log
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.wrapper.StringUtil
import okio.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal fun Any?.print(tag: String = "TAG") {
    Log.e(tag, this.toString())
}

internal fun String.isInValidFile(): Boolean =
    !this.endsWith(".pdf", ignoreCase = true) && !this.endsWith(".txt", ignoreCase = true)

internal fun Exception.getError(): StringUtil {
    return if (this is IOException) {
        StringUtil.StringResource(R.string.check_internet)
    } else {
        this.message?.let {
            StringUtil.DynamicText(it)
        } ?: StringUtil.StringResource(R.string.server_error)
    }
}

internal fun Long.formatTime(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

internal fun getSalutation(): String {
    val dt = Date()
    val timeFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val afternoonFormatter = SimpleDateFormat("yyyy-MM-dd 12:00:00", Locale.getDefault())
    val eveningFormatter = SimpleDateFormat("yyyy-MM-dd 18:00:00", Locale.getDefault())

    val now: Date = timeFormatter.parse(timeFormatter.format(dt)) ?: dt
    val afternoonDate = timeFormatter.parse(afternoonFormatter.format(dt)) ?: dt
    val eveningDate = timeFormatter.parse(eveningFormatter.format(dt)) ?: dt

    return if (now.after(afternoonDate)) {
        if (now.after(eveningDate))
            "Good Evening,"
        else "Good Afternoon,"
    } else {
        "Good Morning,"
    }
}
