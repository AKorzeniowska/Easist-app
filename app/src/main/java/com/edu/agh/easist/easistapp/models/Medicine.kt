package com.edu.agh.easist.easistapp.models

import com.google.gson.annotations.SerializedName

data class Medicine (
    @SerializedName("id") val id: Long? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("dose") val dose: String? = null
)