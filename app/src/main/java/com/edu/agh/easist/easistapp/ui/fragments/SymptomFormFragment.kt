package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.AuthApiConnector
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import com.edu.agh.easist.easistapp.models.SymptomData
import com.edu.agh.easist.easistapp.ui.adapters.PatientRowAdapter
import com.edu.agh.easist.easistapp.ui.models.PatientRowModel
import com.edu.agh.easist.easistapp.utils.getToken
import com.edu.agh.easist.easistapp.utils.openNewFragment
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
        setButtonFunctions()
    }

    private fun setButtonFunctions(){
        submitSymptomForm.setOnClickListener{
            val symptomData = getSymptomData()
            if (symptomData == null)
                Toast.makeText(context, R.string.warning__invalid_data, Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(context, R.string.info__save_successful, Toast.LENGTH_SHORT).show()
                    openNewFragment(activity, UserPanelFragment())
                } else {
                    Toast.makeText(
                            activity,
                            "Error: ${response.message()}",
                            Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(activity,
                        "Error while connecting: ${e.message}",
                        Toast.LENGTH_LONG).show()
            }
        }
    }
}