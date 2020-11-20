package com.edu.agh.easist.easistapp.utils

import java.sql.Time
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

var timeFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

fun parseTimeToString(time: Time): String{
    return time.toString()
}

fun parseStringToTime(string: String): Time?{
    if (timeFormat.parse(string) != null)
        return Time(timeFormat.parse(string)!!.time)
    return null
}