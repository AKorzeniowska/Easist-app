package com.edu.agh.easist.easistapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Role {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("roleName")
    @Expose
    var roleName: String? = null
}