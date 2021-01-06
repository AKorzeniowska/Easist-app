package com.edu.agh.easist.easistapp.models

import com.edu.agh.easist.easistapp.utils.getCurrentDate
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Date
import java.sql.Time
import java.time.Duration
import java.time.LocalTime

class SleepEntryData : Serializable{
        var timeFrom: Time? = null
        var timeTo: Time? = null
        var hourCount: Double? = null
    var date: Date? = null


    fun isValid(): Boolean{
        this.date = getCurrentDate()
        if (timeFrom == null || timeTo == null)
            return false
        return true
    }

    override fun toString(): String {
        return "SleepEntryData(timeFrom=$timeFrom, timeTo=$timeTo, hourCount=$hourCount)"
    }
}