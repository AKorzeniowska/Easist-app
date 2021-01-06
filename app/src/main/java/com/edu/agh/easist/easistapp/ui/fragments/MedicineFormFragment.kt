package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import com.edu.agh.easist.easistapp.models.MedicineData
import com.edu.agh.easist.easistapp.utils.*
import kotlinx.android.synthetic.main.fragment_medicine_form.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class MedicineFormFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_medicine_form, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        showMenu(requireActivity(), false)
        showToolbar(requireActivity(), true)
        setToolbar(requireActivity(), getString(R.string.medicine_form_title), null)
        setButtonFunctions()
    }

    private fun setButtonFunctions(){
        submitMedicineForm.setOnClickListener{
            val medicineData = getMedicineData()
            if (medicineData == null)
                showToast(context, R.string.warning__invalid_data)
            else
                sendAddMedicineRequest(medicineData)
        }
    }

    private fun getMedicineData(): MedicineData?{
        val medicineData = MedicineData()
        medicineData.name = medicineFormNameEditText.text.toString()
        medicineData.dose = medicineFormDoseEditText.text.toString()
        if (medicineData.isValid())
            return medicineData
        return null
    }

    private fun sendAddMedicineRequest(medicineData: MedicineData){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                val response = ResourceApiConntector.apiClient.addMedicine(getToken(activity)!!, medicineData)
                Timber.d(response.toString())
                if (response.isSuccessful) {
                    showToast(context, R.string.info__save_successful)
                    openNewFragment(activity, PatientPanelFragment())
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