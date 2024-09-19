package com.learning.bookshelf.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils{
    fun unixTimestampToYear(unixTimestamp: Long): Int {
        val date = Date(unixTimestamp * 1000)
        val sdf = SimpleDateFormat("yyyy", Locale.getDefault())
        return sdf.format(date).toInt()
    }
}