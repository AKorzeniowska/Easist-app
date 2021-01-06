package com.edu.agh.easist.easistapp.utils

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.ui.fragments.DiaryFormFragmentFirst
import kotlinx.android.synthetic.main.fragment_diary.*

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
    return "Bearer " + sharedPref.getString(KEY_AUTH_TOKEN, null)
}

fun saveUsername(username: String, activity: Activity?){
    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
    with (sharedPref.edit()) {
        putString(KEY_USERNAME, username)
        apply()
    }
}

fun getUsername(activity: Activity?): String? {
    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return null
    return sharedPref.getString(KEY_USERNAME, null)
}

fun saveRole(role: String, activity: Activity?){
    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
    with (sharedPref.edit()) {
        putString(KEY_ROLE, role)
        apply()
    }
}

fun getRole(activity: Activity?): String? {
    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return null
    return sharedPref.getString(KEY_ROLE, null)
}

fun saveBrowsedUser(username: String, activity: Activity?){
    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
    with (sharedPref.edit()) {
        putString(KEY_BROWSED_USERNAME, username)
        apply()
    }
}

fun getBrowsedUser(activity: Activity?): String? {
    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return null
    return sharedPref.getString(KEY_BROWSED_USERNAME, null)
}

fun saveBrowsedUserFullName(username: String, activity: Activity?){
    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
    with (sharedPref.edit()) {
        putString(KEY_BROWSED_FULL_NAME, username)
        apply()
    }
}

fun getBrowsedUserFullName(activity: Activity?): String? {
    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return null
    return sharedPref.getString(KEY_BROWSED_FULL_NAME, null)
}

private const val KEY_AUTH_TOKEN = "auth_token_key"
private const val KEY_USERNAME = "username_key"
private const val KEY_ROLE = "role_key"
private const val KEY_BROWSED_USERNAME = "browsed_key"
private const val KEY_BROWSED_FULL_NAME = "browsed_full_name_key"

const val ROLE_PATIENT = "patient"
const val ROLE_DOCTOR = "doctor"