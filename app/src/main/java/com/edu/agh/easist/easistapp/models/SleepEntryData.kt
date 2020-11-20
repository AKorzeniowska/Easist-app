package com.edu.agh.easist.easistapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Time
import java.time.Duration

class SleepEntryData : Serializable{
        var timeFrom: Time? = null
        var timeTo: Time? = null
        var hourCount: Double? = null

    fun isValid(): Boolean{
        if (timeFrom == null || timeTo == null)
            return false
        return true
    }

    //TODO: kurde godzinki
//    fun countHours(){
//        val duration = Duration.between(
//                timeFrom, timeTo
//        )
//    }
}