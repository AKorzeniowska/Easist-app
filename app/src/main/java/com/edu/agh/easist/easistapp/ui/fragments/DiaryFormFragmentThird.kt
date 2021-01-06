package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import com.edu.agh.easist.easistapp.models.*
import com.edu.agh.easist.easistapp.ui.adapters.MedicineRowAdapter
import com.edu.agh.easist.easistapp.ui.adapters.SymptomRowAdapter
import com.edu.agh.easist.easistapp.ui.models.MedicineRowModel
import com.edu.agh.easist.easistapp.ui.models.SymptomRowModel
import com.edu.agh.easist.easistapp.utils.*
import kotlinx.android.synthetic.main.fragment_diary_form_third.*
import kotlinx.android.synthetic.main.fragment_diary_from_second.*
import kotlinx.android.synthetic.main.fragment_diary_from_second.saveDiaryEntryButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class DiaryFormFragmentThird : Fragment() {
    private lateinit var medicineRowModels: ArrayList<MedicineRowModel>
    private lateinit var diaryEntryData: DiaryEntryData

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diary_form_third, container, false)
        getBundle()
        return view
    }


    override fun onStart() {
        super.onStart()
        showToolbar(requireActivity(), false)
        showMenu(requireActivity(), false)
        setButtonFunctions()
        getUserMedicines()
    }

    private fun getBundle(){
        val bundle = arguments
        val diaryEntryData: DiaryEntryData = bundle!!.getSerializable("diaryEntry") as DiaryEntryData
        this.diaryEntryData = diaryEntryData
    }

    private fun sendDiaryEntrySubmitRequest(){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                val response = ResourceApiConntector.apiClient.addDiaryEntry(getToken(activity)!!, diaryEntryData)
                Timber.d(response.toString())
                if (response.isSuccessful) {
                    showToast(context, R.string.info__save_successful)
                    openNewFragment(activity, DiaryFragment())
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

    private fun getUserMedicines(){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                val response = ResourceApiConntector.apiClient.getMedicines(getToken(activity)!!, getUsername(activity)!!)
                Timber.d(response.toString())
                if (response.isSuccessful && response.body() != null) {
                    setMedicines(response.body()!!)
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

    private fun setMedicines(medicines: Collection<Medicine>){
        medicineRowModels = ArrayList()
        for (medicine in medicines){
            val rowModel = MedicineRowModel(medicine.id!!, medicine.name!!, false)
            medicineRowModels.add(rowModel)
        }
        val adapter = MedicineRowAdapter(medicineRowModels)
        medicineRecyclerView.adapter = adapter
        medicineRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setButtonFunctions(){
        saveDiaryEntryButton.setOnClickListener{
            getMedicineEntries()
            if (!diaryEntryData.isThirdPartValid()) {
                showToast(context, R.string.warning__invalid_data)
            } else {
                sendDiaryEntrySubmitRequest()
            }
        }
    }

    private fun getMedicineEntries(){
        val medicineEntries: MutableList<MedicineEntryData> = mutableListOf()
        for (rowModel in medicineRowModels){
            val medicineEntryData = MedicineEntryData(rowModel)
            medicineEntries.add(medicineEntryData)
        }
        diaryEntryData.medicineEntries = medicineEntries
    }
}
