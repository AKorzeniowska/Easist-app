package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import com.edu.agh.easist.easistapp.models.Medicine
import com.edu.agh.easist.easistapp.models.Patient
import com.edu.agh.easist.easistapp.ui.adapters.DiaryRowAdapter
import com.edu.agh.easist.easistapp.ui.adapters.ListRowAdapter
import com.edu.agh.easist.easistapp.ui.adapters.PatientRowAdapter
import com.edu.agh.easist.easistapp.ui.models.DiaryRowModel
import com.edu.agh.easist.easistapp.ui.models.ListRowModel
import com.edu.agh.easist.easistapp.ui.models.PatientRowModel
import com.edu.agh.easist.easistapp.utils.*
import kotlinx.android.synthetic.main.fragment_medicine_symptom_list.*
import kotlinx.android.synthetic.main.fragment_patients_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class PatientsListFragment : Fragment() {
    lateinit var patientRowModels: ArrayList<PatientRowModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_patients_list, container, false)
        getUserPatients()
        return view
    }

    override fun onStart() {
        super.onStart()
        showMenu(requireActivity(), false)
        showToolbar(requireActivity(), true)
        setToolbar(requireActivity(), getString(R.string.patients), null)
        setButtonFunctions()
        getUserPatients()
    }


    private fun getUserPatients(){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                val response = ResourceApiConntector.apiClient.getDoctorsPatients(getToken(activity)!!)
                Timber.d(response.toString())
                if (response.isSuccessful && response.body() != null) {
                    setPatients(response.body()!!)
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

    private fun getUserInvitedPatients(){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                val response = ResourceApiConntector.apiClient.getPatientsFromActiveInvitations(getToken(activity)!!)
                Timber.d(response.toString())
                if (response.isSuccessful && response.body() != null) {
                    setInvitedPatients(response.body()!!)
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

    private fun setPatients(patients: Collection<Patient>){
        patientRowModels = ArrayList()
        for (patient in patients){
            val patientRowModel = PatientRowModel(patient.id!!, patient.firstName + " " + patient.lastName,
                    null, patient.username!!)
            patientRowModels.add(patientRowModel)
        }
        val adapter = PatientRowAdapter(patientRowModels, requireContext())
        patientEntriesRecycler.adapter = adapter
        patientEntriesRecycler.layoutManager = LinearLayoutManager(context)
        patientEntriesRecycler.addItemDecoration(DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL))
        getUserInvitedPatients()
    }

    private fun setButtonFunctions(){
        floatingActionAddPatient.setOnClickListener{
            openNewFragment(activity, PatientSearchFragment(), addToBackStack = true)
        }
    }

    private fun setInvitedPatients(patients: Collection<Patient>){
        for (patient in patients){
            val patientRowModel = PatientRowModel(patient.id!!, patient.firstName + " " + patient.lastName,
                    getString(R.string.invitation_pending), patient.username!!)
            patientRowModels.add(patientRowModel)
        }
        val adapter = PatientRowAdapter(patientRowModels, requireContext())
        patientEntriesRecycler.adapter = adapter
        patientEntriesRecycler.layoutManager = LinearLayoutManager(context)
    }
}
