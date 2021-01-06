package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import com.edu.agh.easist.easistapp.models.Patient
import com.edu.agh.easist.easistapp.ui.adapters.PatientRowAdapter
import com.edu.agh.easist.easistapp.ui.adapters.PatientSearchRowAdapter
import com.edu.agh.easist.easistapp.ui.models.PatientRowModel
import com.edu.agh.easist.easistapp.ui.models.PatientSearchRowModel
import com.edu.agh.easist.easistapp.utils.getToken
import com.edu.agh.easist.easistapp.utils.showMenu
import com.edu.agh.easist.easistapp.utils.showToast
import kotlinx.android.synthetic.main.fragment_patients_list.*
import kotlinx.android.synthetic.main.fragment_search_patient.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class PatientSearchFragment : Fragment() {
    lateinit var patientSearchRows: ArrayList<PatientSearchRowModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_patient, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        showMenu(requireActivity(), false)
        setButtonFunctions()
    }


    private fun searchPatients() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                val response = ResourceApiConntector.apiClient.getPatientsByUsernameOrLastName(
                        getToken(activity)!!, patientSearchEditText.text.toString())

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

    private fun setPatients(patients: Collection<Patient>) {
        patientSearchRows = ArrayList()
        for (patient in patients) {
            val patientSearchRow = PatientSearchRowModel(patient.id!!, patient.firstName + " " + patient.lastName, patient.age!!, patient.username!!)
            patientSearchRows.add(patientSearchRow)
        }
        val adapter = PatientSearchRowAdapter(patientSearchRows, requireContext())
        patientSearchRecycler.adapter = adapter
        patientSearchRecycler.layoutManager = LinearLayoutManager(context)
    }

    private fun setButtonFunctions(){
        searchPatientButton.setOnClickListener{
            if (patientSearchEditText.text.isNullOrEmpty())
                showToast(context, R.string.warning__invalid_data)
            else
                searchPatients()
        }
    }
}