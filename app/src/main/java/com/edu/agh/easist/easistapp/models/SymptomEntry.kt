package com.edu.agh.easist.easistapp.models

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class SymptomEntry (
    @SerializedName("id") val id: Long? = null,
    @SerializedName("intensity") val intensity: Int? = null,
    @SerializedName("date") val date: Date? = null,
    @SerializedName("comment") val comment: String? = null,
    @SerializedName("symptom") val symptom: Symptom? = null
)