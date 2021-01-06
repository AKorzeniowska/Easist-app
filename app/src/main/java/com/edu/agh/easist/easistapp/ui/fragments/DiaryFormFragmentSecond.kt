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
import com.edu.agh.easist.easistapp.models.*
import com.edu.agh.easist.easistapp.ui.adapters.SymptomRowAdapter
import com.edu.agh.easist.easistapp.ui.models.SymptomRowModel
import com.edu.agh.easist.easistapp.utils.*
import kotlinx.android.synthetic.main.fragment_diary_from_second.*
import kotlinx.android.synthetic.main.fragment_sign_up_first.signUpButton
import kotlinx.android.synthetic.main.fragment_sign_up_second.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber


class DiaryFormFragmentSecond : Fragment() {
    private lateinit var symptomRowModels: ArrayList<SymptomRowModel>
    private lateinit var diaryEntryData: DiaryEntryData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diary_from_second, container, false)
        getBundle()
        return view
    }

    override fun onStart() {
        super.onStart()
        showMenu(requireActivity(), false)
        showToolbar(requireActivity(), false)
        setButtonFunctions()
        getUserSymptoms()
    }

    private fun getBundle(){
        val bundle = arguments
        val diaryEntryData: DiaryEntryData = bundle!!.getSerializable("diaryEntry") as DiaryEntryData
        this.diaryEntryData = diaryEntryData
    }

    private fun getUserSymptoms(){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                val response = ResourceApiConntector.apiClient.getSymptoms(getToken(activity)!!, getUsername(activity)!!)
                Timber.d(response.toString())
                if (response.isSuccessful && response.body() != null) {
                    setSymptoms(response.body()!!)
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

    private fun setSymptoms(symptoms: Collection<Symptom>){
        symptomRowModels = ArrayList()
        for (symptom in symptoms){
            val rowModel = SymptomRowModel(symptom.id!!, symptom.name!!, 0)
            symptomRowModels.add(rowModel)
        }
        val adapter = SymptomRowAdapter(symptomRowModels)
        symptomRecyclerView.adapter = adapter
        symptomRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setButtonFunctions(){
        saveDiaryEntryButton.setOnClickListener{
            getSymptomEntries()
            if (!diaryEntryData.isSecondPartValid()) {
                showToast(context, R.string.warning__invalid_data)
            } else {
                openNewFragmentWithData(activity, DiaryFormFragmentThird(),
                        "diaryEntry", diaryEntryData, addToBackStack = true)
            }
        }
    }

    private fun getSymptomEntries(){
        val symptomEntries: MutableList<SymptomEntryData> = mutableListOf()
        for (rowModel in symptomRowModels){
            val symptomEntryData = SymptomEntryData(rowModel)
            symptomEntries.add(symptomEntryData)
        }
        diaryEntryData.symptomEntries = symptomEntries
    }
}
