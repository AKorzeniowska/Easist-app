package com.edu.agh.easist.easistapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.ui.models.PatientRowModel

class PatientRowAdapter (private val mPatientRows: List<PatientRowModel>) : RecyclerView.Adapter<PatientRowAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.patientEntryNameEditText)!!
        val noteTextView = itemView.findViewById<TextView>(R.id.patientEntryNoteEditText)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val patientRowView = inflater.inflate(R.layout.row_patient_element, parent, false)
        return ViewHolder(patientRowView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rowModel = mPatientRows[position]
        holder.nameTextView.text = rowModel.name
        holder.noteTextView.text = rowModel.note
    }

    override fun getItemCount(): Int {
        return mPatientRows.size
    }
}