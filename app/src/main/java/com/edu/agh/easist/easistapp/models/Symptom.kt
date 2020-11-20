package com.edu.agh.easist.easistapp.models

import com.google.gson.annotations.SerializedName

data class Symptom (
     @SerializedName("id") val id: Long? = null,
     @SerializedName("name") val name: String? = null,
     @SerializedName("comment") val comment: String? = null
)