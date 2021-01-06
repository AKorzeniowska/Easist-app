package com.edu.agh.easist.easistapp.utils

import java.sql.Date
import java.sql.Time
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

var timeFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
var dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

fun parseTimeToString(time: Time): String{
    return time.toString()
}

fun parseStringToTime(string: String): Time?{
    if (timeFormat.parse(string) != null)
//        return LocalTime.parse("10:15:30", DateTimeFormatter.ISO_LOCAL_TIME)
        return Time(timeFormat.parse(string)!!.time)
    return null
}

fun parseDateToString(date: Date): String{
    return dateFormat.format(date)
}

fun parseStringToDate(string: String): LocalDate?{
    return LocalDate.parse(string)
}

fun getCurrentDate(): Date{
//    return LocalDate.now()
    return Date(Calendar.getInstance().time.time)
}

fun getCurrentDateAsString(): String{
    return parseDateToString(getCurrentDate())
}