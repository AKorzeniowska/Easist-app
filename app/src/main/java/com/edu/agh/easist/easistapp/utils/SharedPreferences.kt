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

private const val KEY_AUTH_TOKEN = "auth_token_key"