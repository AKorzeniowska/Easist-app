package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.AuthApiConnector
import com.edu.agh.easist.easistapp.utils.*
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

    override fun onStart() {
        super.onStart()
        showMenu(requireActivity(), false)
        showToolbar(requireActivity(), false)
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
            showToast(context, R.string.warning__empty_field)
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
                    showToast(context, R.string.info__login_successful)
                    saveToken(response.body()!!.accessToken, activity)
                    saveUsername(usernameSignInEditText.text.toString(), activity)
                    openNewFragment(activity, SplashScreenFragment())
                } else if (response.code() == 400){
                    showToast(
                            activity,
                            R.string.warning__invalid_credentials)
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
