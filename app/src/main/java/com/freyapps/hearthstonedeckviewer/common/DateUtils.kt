package com.freyapps.hearthstonedeckviewer.common

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun String.convertDateToLong(format: String): Long {
    return try {
        val df = SimpleDateFormat(format, Locale.forLanguageTag("RU"))
        df.parse(this)?.time ?: 0
    } catch (e: Exception) {
        Log.e(TAG, e.message.toString())
        0
    }
}

fun Long.convertLongToFormattedDate(format: String): String {
    return try {
        val df = SimpleDateFormat(format, Locale.getDefault())
        df.format(this)
    } catch (e: Exception) {
        Log.e(TAG, e.message.toString())
        ""
    }
}

const val LONG_DATE_TIME = "dd MMMM yyyy HH:mm"
const val DAY_MONTH = "dd MMMM"
const val DAY_MONTH_YEAR = "dd MMMM yyyy"
private const val TAG = "DateUtils"