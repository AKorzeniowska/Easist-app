package com.edu.agh.easist.easistapp.models

import com.google.gson.annotations.SerializedName
import java.sql.Time

data class SleepEntry (
        @SerializedName("id") val id: Long? = null,
        @SerializedName("timeFrom") val timeFrom: Time? = null,
        @SerializedName("timeTo") val timeTo: Time? = null,
        @SerializedName("hourCount") val hourCount: Double? = null
)