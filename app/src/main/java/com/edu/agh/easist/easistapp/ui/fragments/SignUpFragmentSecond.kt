package com.edu.agh.easist.easistapp.ui.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.AuthApiConnector
import com.edu.agh.easist.easistapp.models.UserData
import com.edu.agh.easist.easistapp.utils.openNewFragment
import com.edu.agh.easist.easistapp.utils.showMenu
import com.edu.agh.easist.easistapp.utils.showToast
import kotlinx.android.synthetic.main.fragment_sign_up_first.*
import kotlinx.android.synthetic.main.fragment_sign_up_first.signUpButton
import kotlinx.android.synthetic.main.fragment_sign_up_second.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class SignUpFragmentSecond : Fragment() {

    private lateinit var userData: UserData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_second, container, false)
        getBundle()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (userData.role == UserData.PATIENT){
            address1SignUpEdit.visibility = View.GONE
            address2SignUpEdit.visibility = View.GONE
        } else if (userData.role == UserData.DOCTOR){
            ageSignUpEdit.visibility = View.GONE
        }

    }

    override fun onStart() {
        super.onStart()
        showMenu(requireActivity(), false)
        setButtonFunctions()
    }

    private fun getBundle(){
        val bundle = arguments
        val userData: UserData = bundle!!.getSerializable("userData") as UserData
        this.userData = userData
    }

    private fun setButtonFunctions(){
        signUpButton.setOnClickListener{
            signUserUp()
        }
    }

    private fun signUserUp(){
        userData.firstName = firstNameSignUpEditText.text.toString()
        userData.lastName = lastNameSignUpEditText.text.toString()
        userData.phoneNumber = phoneNumberSignUpEditText.text.toString()

        if (userData.role == UserData.PATIENT){
            userData.age = ageSignUpEditText.text.toString().toInt()
        } else if (userData.role == UserData.DOCTOR){
            userData.address1 = address1SignUpEditText.text.toString()
            userData.address2 = address2SignUpEditText.text.toString()
        }

        if (userData.isSecondPartValid()){
            sendRegisterRequest()
        } else {
            showToast(context, R.string.warning__invalid_data)
        }
    }

    private fun sendRegisterRequest(){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = AuthApiConnector.apiClient.register(userData)
                Timber.d(response.toString())
                if (response.isSuccessful) {
//                    val data = response.body()!!
                    showToast(context, R.string.info__signup_successful)
                    openNewFragment(activity, SignInFragment())
                } else {
                    showToast(
                        activity,
                        "Error : ${response.message()}")
                }

            } catch (e: Exception) {
                showToast(activity,
                    "Error while connecting: ${e.message}")
            }
        }
    }
}