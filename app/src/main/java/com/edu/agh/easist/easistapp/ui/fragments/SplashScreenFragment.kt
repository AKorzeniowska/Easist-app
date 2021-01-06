package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.AuthApiConnector
import com.edu.agh.easist.easistapp.logic.RoleApiConnector
import com.edu.agh.easist.easistapp.ui.adapters.PatientRowAdapter
import com.edu.agh.easist.easistapp.ui.models.PatientRowModel
import com.edu.agh.easist.easistapp.utils.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class SplashScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)

        return view
    }

    override fun onStart() {
        super.onStart()
        showMenu(requireActivity(), false)
        sendLoginRequest()
    }

    private fun sendLoginRequest(){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val token = getToken(activity)!!
                val response = RoleApiConnector.apiClient.getRole(token)
                Timber.d(response.toString())
                if (response.isSuccessful && response.body() != null) {
                    saveRole(response.body()!!, activity)
                    if (response.body() == "patient")
                        openNewFragment(activity, DiaryFragment())
                    if (response.body() == "doctor")
                        openNewFragment(activity, PatientsListFragment())
                } else {
                    showToast(
                        activity,
                        "Error: ${response.message()}")
                }
            } catch (e: Exception) {
                showToast(activity,
                    "Error while connecting: ${e.message}")
            }
        }
    }
}