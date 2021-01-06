package com.edu.agh.easist.easistapp.models

import com.google.gson.annotations.SerializedName

data class Doctor (
        @SerializedName("id") val id: Long? = null,
        @SerializedName("firstName") val firstName: String? = null,
        @SerializedName("lastName") val lastName: String? = null,
        @SerializedName("email") val email: String? = null,
        @SerializedName("phoneNumber") val phoneNumber: String? = null,
        @SerializedName("address1") val address1: String? = null,
        @SerializedName("address2") val address2: String? = null,
        @SerializedName("username") val username: String? = null,
        @SerializedName("patients") val patients: Set<Patient>? = null
)