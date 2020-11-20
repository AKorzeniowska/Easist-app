package com.edu.agh.easist.easistapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.AuthApiConnector
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)



        GlobalScope.launch(Dispatchers.Main) {
            // Try catch block to handle exceptions when calling the API.
            try {
                val response = AuthApiConnector.apiClient.login(username = "testtest12", password = "testpass")
                if (response.isSuccessful && response.body() != null) {
                    // Retrieve data.
                    val data = response.body()!!
                    Toast.makeText(activity, ":)", Toast.LENGTH_LONG).show()
//                    val response4 = ResourceApiConntector.apiClient.getAll("Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDMwNjgzNjQsInVzZXJfbmFtZSI6InRlc3R0ZXN0MTIiLCJhdXRob3JpdGllcyI6WyJST0xFX2FkbWluIl0sImp0aSI6IjFlMTRlNWMyLWRhYmEtNDYxYy05Mzg2LWU5ZWY5Y2ViZjE5NiIsImNsaWVudF9pZCI6ImZvb0NsaWVudElkIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.UoZfvxPOzhrK8sj6XPgsTkE6GCkUYa4pvC_O-PJV6zU")
//                    Toast.makeText(activity, response4.body()!!.size, Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(
                        activity,
                        "Error Occurred: ${response.message()}",
                        Toast.LENGTH_LONG).show()
                }
//                val response3 = AuthApiConnector.apiClient.register("test", "fsdfss", "fsdf", "patient")

            } catch (e: Exception) {
                Toast.makeText(activity,
                    "Error Occurred: ${e.message}",
                    Toast.LENGTH_LONG).show()
            }
        }
        return root
    }



}