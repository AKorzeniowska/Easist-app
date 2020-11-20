package com.edu.agh.easist.easistapp.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.ui.fragments.SignUpFragmentSecond
import java.io.Serializable

fun openNewFragment(activity: FragmentActivity?, fragment: Fragment, addToBackStack: Boolean = false){
    val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
    ft?.replace(R.id.container, fragment, fragment.javaClass.simpleName)
    if (addToBackStack) {
        ft?.addToBackStack(null)
    }
    ft?.commit()
}

fun openNewFragmentWithData(activity: FragmentActivity?,
                            fragment: Fragment,
                            dataName: String,
                            data: Serializable,
                            addToBackStack: Boolean = false){
    val ft: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
    ft?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

    val bundle = Bundle()
    bundle.putSerializable(dataName, data)
    fragment.arguments = bundle
    ft?.replace(R.id.container, fragment, fragment.javaClass.simpleName)
    if (addToBackStack) {
        ft?.addToBackStack(null)
    }
    ft?.commit()
}

