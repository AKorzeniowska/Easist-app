package com.edu.agh.easist.easistapp.models

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class MedicineEntry (
        @SerializedName("id") val id: Long? = null,
        @SerializedName("intensity") val intensity: Int? = null,
        @SerializedName("taken") val taken: Boolean? = null,
        @SerializedName("medicine") val medicine: Medicine? = null
)