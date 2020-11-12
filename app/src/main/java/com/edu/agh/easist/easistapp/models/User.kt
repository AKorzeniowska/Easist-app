package com.edu.agh.easist.easistapp.models

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class User {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("username")
    @Expose
    var username: String? = null

    @SerializedName("firstName")
    @Expose
    var firstName: Any? = null

    @SerializedName("lastName")
    @Expose
    var lastName: Any? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("accountNonExpired")
    @Expose
    var accountNonExpired = false

    @SerializedName("accountNonLocked")
    @Expose
    var accountNonLocked = false

    @SerializedName("credentialsNonExpired")
    @Expose
    var credentialsNonExpired = false

    @SerializedName("enabled")
    @Expose
    var enabled = false

    @SerializedName("roles")
    @Expose
    var roles: List<Role>? = null
}