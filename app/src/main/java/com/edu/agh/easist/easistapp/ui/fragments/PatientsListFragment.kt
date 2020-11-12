package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.ui.adapters.DiaryRowAdapter
import com.edu.agh.easist.easistapp.ui.adapters.PatientRowAdapter
import com.edu.agh.easist.easistapp.ui.models.DiaryRowModel
import com.edu.agh.easist.easistapp.ui.models.PatientRowModel

class PatientsListFragment : Fragment() {
    lateinit var patientRowModels: ArrayList<PatientRowModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_patients_list, container, false)
        val recycler = view.findViewById<RecyclerView>(R.id.patientEntriesRecycler)
        patientRowModels = ArrayList()
        patientRowModels.add(PatientRowModel(1, "Jan Kowalski", "jasdhk sdhjfsjdfjs sjdf jsdfhg sjdhgfjsdffsdfsdfsdf sdf sdf sdf"))
        patientRowModels.add(PatientRowModel(2, "Andrzej Nowak", "msdfhksjdksdf sdfhskdjfksjdf sdjkfhskdjfhksjdfhksjdfksjdfhksdjfksjdfhksjhfkjsdfkjsdhfksjdhfkjsdhfksjhfksjdfhkjsdhf nsdfnhdsjf"))
        val adapter = PatientRowAdapter(patientRowModels)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}
