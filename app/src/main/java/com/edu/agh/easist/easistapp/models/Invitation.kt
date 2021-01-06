package com.edu.agh.easist.easistapp.models

import com.google.gson.annotations.SerializedName

data class Invitation (
        @SerializedName("id") val id: Long? = null,
        @SerializedName("isActive") val isActive: Boolean? = null,
        @SerializedName("gotAccepted") val gotAccepted: Boolean? = null,
        @SerializedName("patient") val patient: Patient? = null,
        @SerializedName("doctor") val doctor: Doctor? = null
)