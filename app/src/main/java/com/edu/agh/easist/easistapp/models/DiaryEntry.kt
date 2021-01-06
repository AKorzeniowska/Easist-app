package com.edu.agh.easist.easistapp.models

import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.time.LocalDate

data class DiaryEntry (
        @SerializedName("id") val id: Long? = null,
        @SerializedName("date") val date: Date? = null,
        @SerializedName("content") val content: String? = null,
        @SerializedName("mood") val mood: Int? = null,
        @SerializedName("sleepEntry") val sleepEntry: SleepEntry? = null,
        @SerializedName("symptomEntries") val symptomEntries: List<SymptomEntry>? = null,
        @SerializedName("medicineEntries") val medicineEntries: List<MedicineEntry>? = null
)