package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import com.edu.agh.easist.easistapp.models.Patient
import com.edu.agh.easist.easistapp.utils.*
import kotlinx.android.synthetic.main.fragment_patient_panel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class PatientPanelFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_patient_panel, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        showMenu(requireActivity(), true)
        setButtonFunctions()
        if (getRole(activity) == ROLE_PATIENT) {
            getPatientData(getUsername(activity)!!)
        } else {
            getPatientData(getBrowsedUser(activity)!!)
            showToolbar(requireActivity(), true)
            setToolbar(requireActivity(), getBrowsedUserFullName(activity)!!, getBrowsedUser(activity))
        }
    }

    private fun setButtonFunctions(){
        listSymptomsButton.setOnClickListener{
            openNewFragment(activity, SymptomListFragment(), addToBackStack = true)
        }
        listMedicinesButton.setOnClickListener{
            openNewFragment(activity, MedicineListFragment(), addToBackStack = true)
        }
    }

    private fun setPatientData(patient: Patient){
        firstAndLastNameInfoTextProfile.text = getString(R.string.firstname_and_lastname_info)
        firstAndLastNameTextProfile.text = getString(R.string.formatter__firstname_and_lastname,
                patient.firstName, patient.lastName)
        ageTextProfile.text = getString(R.string.formatter__age, patient.age)
        ageInfoTextProfile.text = getString(R.string.age_info)
        emailAddressTextProfile.text = patient.email
        patientEmailAddressInfoTextProfile.text = getString(R.string.email_address_info)
        phoneNumberTextProfile.text = patient.phoneNumber
        phoneNumberInfoTextProfile.text = getString(R.string.phone_number_info)
        usernameInfoTextProfile.text = getString(R.string.username_info)
        usernameTextProfile.text = patient.username
    }

    private fun getPatientData(username: String){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                val response = ResourceApiConntector.apiClient.getPatient(getToken(activity)!!, username)
                Timber.d(response.toString())
                if (response.isSuccessful && response.body() != null) {
                    setPatientData(response.body()!!)
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