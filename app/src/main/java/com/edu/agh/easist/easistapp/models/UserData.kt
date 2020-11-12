package com.edu.agh.easist.easistapp.models

import java.io.Serializable

class UserData : Serializable {
    var username: String? = null
    var password: String? = null
    var role: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var emailAddress: String? = null
    var phoneNumber: String? = null
    var age: Int? = null
    var address1: String? = null
    var address2: String? = null

    companion object{
        const val PATIENT = "patient"
        const val DOCTOR = "doctor"
    }

    fun isFirstPartValid(): Boolean{
        //TODO: konfiguracja!
        if (username.isNullOrEmpty() || username!!.isBlank() || username!!.length < 5 || username!!.contains(" ")){
            return false
        }
        if (password.isNullOrEmpty() || password!!.isBlank() || password!!.length < 5 || password!!.contains(" ")){
            return false
        }
        if (role.isNullOrEmpty() || (role != UserData.DOCTOR && role != UserData.PATIENT)){
            return false
        }
        if (emailAddress.isNullOrEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress!!).matches()){
            return false
        }
        return true
    }

    fun isSecondPartValid(): Boolean{
        if (!isFirstPartValid())
            return false
        //TODO: konfiguracja!
        if (role == UserData.PATIENT){
            if (age == null || age!! > 150 || age!! < 5){
                return false
            }
        }
        if (role == UserData.DOCTOR){
            if (address1.isNullOrEmpty() || address1!!.isBlank()){
                return false
            }
            if (address2.isNullOrEmpty() || address2!!.isBlank()){
                return false
            }
        }
        if (firstName.isNullOrEmpty() || firstName!!.isBlank()){
            return false
        }
        if (lastName.isNullOrEmpty() || lastName!!.isBlank()){
            return false
        }
        if (phoneNumber.isNullOrEmpty() || !android.util.Patterns.PHONE.matcher(phoneNumber!!).matches()){
            return false
        }
        return true
    }
}


