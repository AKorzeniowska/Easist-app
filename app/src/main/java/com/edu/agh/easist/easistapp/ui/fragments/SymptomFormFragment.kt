package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import com.edu.agh.easist.easistapp.models.SymptomData
import com.edu.agh.easist.easistapp.utils.*
import kotlinx.android.synthetic.main.fragment_symptom_form.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class SymptomFormFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_symptom_form, container, false)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        showMenu(requireActivity(), false)
        showToolbar(requireActivity(), true)
        setToolbar(requireActivity(), getString(R.string.symptom_form_title), null)
        setButtonFunctions()
    }

    private fun setButtonFunctions(){
        submitSymptomForm.setOnClickListener{
            val symptomData = getSymptomData()
            if (symptomData == null)
                showToast(context, R.string.warning__invalid_data)
            else
                sendAddSymptomRequest(symptomData)
        }
    }

    private fun getSymptomData(): SymptomData?{
        val symptomData = SymptomData()
        symptomData.name = symptomFormNameEditText.text.toString()
        symptomData.comment = symptomFormCommentEditText.text.toString()
        if (symptomData.isValid())
            return symptomData
        return null
    }

    private fun sendAddSymptomRequest(symptomData: SymptomData){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                val response = ResourceApiConntector.apiClient.addSymptom(getToken(activity)!!, symptomData)
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