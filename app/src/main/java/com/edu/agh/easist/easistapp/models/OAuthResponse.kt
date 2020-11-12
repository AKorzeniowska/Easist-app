package com.edu.agh.easist.easistapp.models

import com.google.gson.annotations.SerializedName

data class OAuthResponse (

    @SerializedName("access_token") val accessToken : String? = null,
    @SerializedName("token_type") val tokenType : String? = null,
    @SerializedName("expires_in") val expiresIn : Int? = null,
    @SerializedName("scope") val scope : String? = null,
    @SerializedName("jti") val jti : String? = null,

    @SerializedName("error") val error : String? = null,
    @SerializedName("error_description") val errorDescription : String? = null,
)