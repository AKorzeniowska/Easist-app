package com.edu.agh.easist.easistapp.models

import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Time
import java.time.LocalTime

data class SleepEntry (
        @SerializedName("id") val id: Long? = null,
        @SerializedName("date") val date: Date? = null,
        @SerializedName("timeFrom") val timeFrom: String? = null,
        @SerializedName("timeTo") val timeTo: String? = null,
        @SerializedName("hourCount") val hourCount: Double? = null
)