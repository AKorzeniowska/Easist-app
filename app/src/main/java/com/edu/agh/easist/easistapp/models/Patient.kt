package com.edu.agh.easist.easistapp.models

import com.google.gson.annotations.SerializedName

data class Patient (
        @SerializedName("id") val id: Long? = null,
        @SerializedName("firstName") val firstName: String? = null,
        @SerializedName("lastName") val lastName: String? = null,
        @SerializedName("email") val email: String? = null,
        @SerializedName("phoneNumber") val phoneNumber: String? = null,
        @SerializedName("age") val age: Int? = null,
        @SerializedName("username") val username: String? = null,
        @SerializedName("doctor") val doctor: Doctor? = null,
        @SerializedName("diaryEntries") val diaryEntries: Set<DiaryEntry>? = null,
        @SerializedName("symptoms") val symptoms: Set<Symptom>? = null
)