package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.models.UserData
import com.edu.agh.easist.easistapp.utils.openNewFragment
import com.edu.agh.easist.easistapp.utils.openNewFragmentWithData
import kotlinx.android.synthetic.main.fragment_sign_up_first.*

class SignUpFragmentFirst : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_first, container, false)
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
        signUpButton.setOnClickListener{
            submitSignIn()
        }
        redirectToSignInButton.setOnClickListener{
            openNewFragment(activity, SignInFragment())
        }
    }

    private fun submitSignIn(){
        if (passwordSignUpEditText.text.toString() != repeatPasswordSignUpEditText.text.toString()){
            Toast.makeText(context, R.string.warning__passwords_are_different, Toast.LENGTH_SHORT).show()
            return
        }

        val userData = UserData()
        userData.username = usernameSignUpEditText.text.toString()
        userData.password = passwordSignUpEditText.text.toString()
        userData.emailAddress = emailSignUpEditText.text.toString()
        when {
            patientRadioButton.isChecked -> userData.role = UserData.PATIENT
            doctorRadioButton.isChecked -> userData.role = UserData.DOCTOR
        }

        if (userData.isFirstPartValid()){
            openNewFragmentWithData(activity, SignUpFragmentSecond(), "userData", userData)
        } else {
            Toast.makeText(context, R.string.warning__invalid_data, Toast.LENGTH_SHORT).show()
        }
    }
}
