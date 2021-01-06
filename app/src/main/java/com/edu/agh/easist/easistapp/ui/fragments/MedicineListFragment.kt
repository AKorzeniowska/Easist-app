package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import com.edu.agh.easist.easistapp.models.Medicine
import com.edu.agh.easist.easistapp.ui.adapters.ListRowAdapter
import com.edu.agh.easist.easistapp.ui.adapters.MedicineRowAdapter
import com.edu.agh.easist.easistapp.ui.models.ListRowModel
import com.edu.agh.easist.easistapp.ui.models.MedicineRowModel
import com.edu.agh.easist.easistapp.utils.*
import kotlinx.android.synthetic.main.fragment_diary.*
import kotlinx.android.synthetic.main.fragment_diary_form_third.*
import kotlinx.android.synthetic.main.fragment_diary_from_second.*
import kotlinx.android.synthetic.main.fragment_medicine_symptom_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class MedicineListFragment : Fragment() {
    private lateinit var rowModels: ArrayList<ListRowModel>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_medicine_symptom_list, container, false)
        return view
    }


    override fun onStart() {
        super.onStart()
        showMenu(requireActivity(), false)
        showToolbar(requireActivity(), true)
        setButtonFunctions()
        if (getRole(activity) ==  ROLE_PATIENT) {
            getUserMedicines(getUsername(activity)!!)
            setToolbar(requireActivity(), getString(R.string.medicine_list_info), null)
        } else {
            floatingActionAdd.visibility = View.INVISIBLE
            getUserMedicines(getBrowsedUser(activity)!!)
            setToolbar(requireActivity(), getBrowsedUserFullName(activity)!!,
                    getString(R.string.medicine_list_info))
        }
    }


    private fun getUserMedicines(username: String){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                val response = ResourceApiConntector.apiClient.getMedicines(getToken(activity)!!, username)
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
        rowModels = ArrayList()
        for (medicine in medicines){
            val rowModel = ListRowModel(medicine.id!!,
                    getString(R.string.formatter__medicine, medicine.name, medicine.dose), "med")
            rowModels.add(rowModel)
        }
        val adapter = ListRowAdapter(rowModels)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL))
    }

    private fun setButtonFunctions(){
        floatingActionAdd.setOnClickListener{
            openNewFragment(activity, MedicineFormFragment(), addToBackStack = true)
        }
    }
}