package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import com.edu.agh.easist.easistapp.models.Doctor
import com.edu.agh.easist.easistapp.models.Invitation
import com.edu.agh.easist.easistapp.models.InvitationData
import com.edu.agh.easist.easistapp.utils.*
import kotlinx.android.synthetic.main.fragment_doctor_panel.*
import kotlinx.android.synthetic.main.fragment_patient_panel.emailAddressTextProfile
import kotlinx.android.synthetic.main.fragment_patient_panel.firstAndLastNameInfoTextProfile
import kotlinx.android.synthetic.main.fragment_patient_panel.firstAndLastNameTextProfile
import kotlinx.android.synthetic.main.fragment_patient_panel.phoneNumberTextProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DoctorPanelFragment : Fragment(){
    private lateinit var invitation: Invitation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_doctor_panel, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        showMenu(requireActivity(), true)
        setButtonFunctions()
        getDoctorData()
    }

    private fun setButtonFunctions(){
        acceptDoctorInvitationButton.setOnClickListener{
            acceptInvitation()
        }
        declineDoctorInvitationButton.setOnClickListener{
            declineInvitation()
        }
    }

    private fun setDoctorData(doctor: Doctor){
        firstAndLastNameInfoTextProfile.text = getString(R.string.firstname_and_lastname_info)
        firstAndLastNameTextProfile.text = getString(R.string.formatter__firstname_and_lastname,
                doctor.firstName, doctor.lastName)
        emailAddressTextProfile.text = doctor.email
        phoneNumberTextProfile.text = doctor.phoneNumber
        phoneInfoTextProfile.text = getString(R.string.phone_number_info)
        addressInfoTextProfile.text = getString(R.string.address_info)
        addressTextProfile.text = getString(R.string.formatter__address, doctor.address1, doctor.address2)
        emailAddressInfoTextProfile.text = getString(R.string.email_address_info)
    }

    private fun getDoctorData(){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                if (getRole(activity) == ROLE_DOCTOR) {
                    val response = ResourceApiConntector.apiClient.getDoctor(getToken(activity)!!)
                    if (response.isSuccessful && response.body() != null) {
                        setDoctorData(response.body()!!)
                    } else {
                        showToast(
                                activity,
                                "Error: ${response.message()}")
                    }
                }

                if (getRole(activity) == ROLE_PATIENT){
                    val response = ResourceApiConntector.apiClient.getPatient(getToken(activity)!!, getUsername(activity)!!)
                    if (response.isSuccessful && response.body() != null) {
                        if(response.body()!!.doctor == null){
                            checkInvitations()
                        } else
                            setDoctorData(response.body()!!.doctor!!)
                    } else {
                        showToast(
                                activity,
                                "Error: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                showToast(activity,
                        "Error while connecting: ${e.message}")
            }
        }
    }

    private fun checkInvitations(){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                    val response = ResourceApiConntector.apiClient.getActiveInvitation(getToken(activity)!!)
                    if (response.isSuccessful) {
                        if (response.body() == null)
                            noDoctorAssignedText.visibility = View.VISIBLE
                        else {
                            invitation = response.body()!!
                            setDoctorData(response.body()!!.doctor!!)
                            acceptDoctorInvitationButton.visibility = View.VISIBLE
                            declineDoctorInvitationButton.visibility = View.VISIBLE
                        }
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

    private fun acceptInvitation(){
        val invitationData = InvitationData(invitation)
        invitationData.isActive = false
        invitationData.gotAccepted = true

        updateInvitation(invitationData)
    }

    private fun declineInvitation(){
        val invitationData = InvitationData(invitation)
        invitationData.isActive = false
        invitationData.gotAccepted = false

        updateInvitation(invitationData)
    }

    private fun updateInvitation(invitationData: InvitationData){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                val response = ResourceApiConntector.apiClient.updateInvitation(getToken(activity)!!, invitationData)
                if (response.isSuccessful) {
                    showToast(activity, R.string.info__save_successful)
                    openNewFragment(activity, DoctorPanelFragment())
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