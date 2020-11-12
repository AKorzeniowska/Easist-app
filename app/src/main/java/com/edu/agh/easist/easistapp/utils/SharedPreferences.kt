package com.edu.agh.easist.easistapp.utils

import android.app.Activity
import android.content.Context

fun saveToken(token: String?, activity: Activity?){
    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
    if (token == null) return
    with (sharedPref.edit()) {
        putString(KEY_AUTH_TOKEN, token)
        apply()
    }
}

fun getToken(activity: Activity?): String?{
    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return null
    return sharedPref.getString(KEY_AUTH_TOKEN, null)
}

private const val KEY_AUTH_TOKEN = "auth_token_key"