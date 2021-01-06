package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import com.edu.agh.easist.easistapp.models.DiaryEntry
import com.edu.agh.easist.easistapp.models.DiaryEntryData
import com.edu.agh.easist.easistapp.ui.adapters.MedicineRowAdapter
import com.edu.agh.easist.easistapp.ui.adapters.SymptomRowAdapter
import com.edu.agh.easist.easistapp.ui.models.MedicineRowModel
import com.edu.agh.easist.easistapp.ui.models.SymptomRowModel
import com.edu.agh.easist.easistapp.utils.*
import kotlinx.android.synthetic.main.fragment_diary.*
import kotlinx.android.synthetic.main.fragment_diary_entry_details.*
import kotlinx.android.synthetic.main.fragment_diary_from_second.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class DiaryEntryDetailsFragment : Fragment() {
    private var diaryEntryId: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_diary_entry_details, container, false)
        getBundle()
        return view
    }

    override fun onStart() {
        super.onStart()
        showMenu(requireActivity(), false)
        showToolbar(requireActivity(), true)
        if (getRole(activity) == ROLE_PATIENT) {
            getDiaryEntry(getUsername(activity)!!)
        } else {
            getDiaryEntry(getBrowsedUser(activity)!!)
        }
    }

    private fun getBundle() {
        val bundle = arguments
        val diaryEntryData: Long = bundle!!.getSerializable("diaryEntryId") as Long
        this.diaryEntryId = diaryEntryData
    }

    private fun getDiaryEntry(username: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                val response = ResourceApiConntector.apiClient.getDiaryEntryById(getToken(activity)!!,
                        username, diaryEntryId)

                if (response.isSuccessful && response.body() != null) {
                    setDiaryEntry(response.body()!!)
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

    private fun setDiaryEntry(diaryEntry: DiaryEntry){
        if (getRole(activity)!! == ROLE_PATIENT){
            setToolbar(requireActivity(), parseDateToString(diaryEntry.date!!), null)
        } else {
            setToolbar(requireActivity(), getBrowsedUserFullName(activity)!!, parseDateToString(diaryEntry.date!!))
        }

        sleepTimeTo.text = getString(R.string.formatter__woke_up_hour, diaryEntry.sleepEntry!!.timeFrom!!
                .substring(0, diaryEntry.sleepEntry.timeFrom!!.length - 3))
        sleepTimeFrom.text = getString(R.string.formatter__went_sleep_hour, diaryEntry.sleepEntry.timeTo!!
                .substring(0, diaryEntry.sleepEntry.timeTo!!.length - 3))
        diaryEntryContent.text = diaryEntry.content
        setImageView(diaryEntry.mood!!, diaryEntryDetailsImageView)

        val symptomRowModels: ArrayList<SymptomRowModel> = ArrayList()
        for (symptomEntry in diaryEntry.symptomEntries!!){
            val symptom = symptomEntry.symptom
            val rowModel = SymptomRowModel(symptom!!.id!!, symptom.name!!, symptomEntry.intensity!!)
            symptomRowModels.add(rowModel)
        }
        val sAdapter = SymptomRowAdapter(symptomRowModels, editable = false)
        symptomsRecycler.adapter = sAdapter
        symptomsRecycler.layoutManager = LinearLayoutManager(context)

        val medicineRowModels: ArrayList<MedicineRowModel> = ArrayList()
        for (medicineEntry in diaryEntry.medicineEntries!!){
            val medicine = medicineEntry.medicine
            val rowModel = MedicineRowModel(medicine!!.id!!, medicine.name!!, medicineEntry.taken!!)
            medicineRowModels.add(rowModel)
        }
        val mAdapter = MedicineRowAdapter(medicineRowModels, editable = false)
        medicinesRecycler.adapter = mAdapter
        medicinesRecycler.layoutManager = LinearLayoutManager(context)
    }

}