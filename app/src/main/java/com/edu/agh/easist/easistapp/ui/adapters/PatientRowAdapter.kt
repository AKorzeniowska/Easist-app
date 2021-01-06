package com.edu.agh.easist.easistapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import com.edu.agh.easist.easistapp.models.InvitationData
import com.edu.agh.easist.easistapp.ui.fragments.DiaryFragment
import com.edu.agh.easist.easistapp.ui.fragments.PatientsListFragment
import com.edu.agh.easist.easistapp.ui.models.PatientRowModel
import com.edu.agh.easist.easistapp.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PatientRowAdapter (private val mPatientRows: List<PatientRowModel>, private val context: Context) : RecyclerView.Adapter<PatientRowAdapter.ViewHolder>() {

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
        if (rowModel.note != null) {
            holder.noteTextView.text = rowModel.note
        } else {
            holder.noteTextView.visibility = View.GONE

            val activity = (context as FragmentActivity)
            holder.itemView.setOnClickListener {
                saveBrowsedUser(rowModel.username, activity)
                saveBrowsedUserFullName(rowModel.name, activity)
                openNewFragmentWithData(activity, DiaryFragment(), "patient_username",
                        rowModel.username, addToBackStack = true)
            }
        }
    }

    override fun getItemCount(): Int {
        return mPatientRows.size
    }
}