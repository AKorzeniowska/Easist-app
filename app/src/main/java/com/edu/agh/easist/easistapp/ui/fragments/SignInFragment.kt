package com.edu.agh.easist.easistapp.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.AuthApiConnector
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import com.edu.agh.easist.easistapp.utils.openNewFragment
import com.edu.agh.easist.easistapp.utils.saveToken
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class SignInFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        setButtonFunctions()
    }

    private fun setButtonFunctions(){
        signInButton.setOnClickListener {
            signUserIn()
        }

        redirectToSignUpButton.setOnClickListener{
            openNewFragment(activity, SignUpFragmentFirst())
        }
    }

    private fun signUserIn(){
        val username = usernameSignInEditText.text.toString()
        val password = passwordSignInEditText.text.toString()
        if (username.isEmpty() || password.isEmpty()){
            Toast.makeText(context, R.string.warning__empty_field, Toast.LENGTH_SHORT).show()
            return
        }
        sendLoginRequest(username, password)
    }

    private fun sendLoginRequest(username: String, password: String){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = AuthApiConnector.apiClient.login(username = username, password = password)
                Timber.d(response.toString())
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(context, R.string.info__login_successful, Toast.LENGTH_SHORT).show()
                    saveToken(response.body()!!.accessToken, activity)
                    openNewFragment(activity, DiaryFragment())
                } else if (response.code() == 400){
                    Toast.makeText(
                        activity,
                        R.string.warning__invalid_credentials,
                        Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(
                        activity,
                        "Error: ${response.message()}",
                        Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(activity,
                    "Error while connecting: ${e.message}",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}
