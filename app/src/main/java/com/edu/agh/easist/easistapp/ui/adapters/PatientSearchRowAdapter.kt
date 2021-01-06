package com.edu.agh.easist.easistapp.ui.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import com.edu.agh.easist.easistapp.models.Invitation
import com.edu.agh.easist.easistapp.models.InvitationData
import com.edu.agh.easist.easistapp.ui.fragments.DiaryEntryDetailsFragment
import com.edu.agh.easist.easistapp.ui.fragments.PatientsListFragment
import com.edu.agh.easist.easistapp.ui.models.PatientRowModel
import com.edu.agh.easist.easistapp.ui.models.PatientSearchRowModel
import com.edu.agh.easist.easistapp.utils.getToken
import com.edu.agh.easist.easistapp.utils.getUsername
import com.edu.agh.easist.easistapp.utils.openNewFragment
import com.edu.agh.easist.easistapp.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PatientSearchRowAdapter(private val mPatientSearchRows: List<PatientSearchRowModel>, private val context: Context) : RecyclerView.Adapter<PatientSearchRowAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.patientNameTextView)!!
        val ageTextView = itemView.findViewById<TextView>(R.id.patientAgeTextView)!!
        val usernameTextView = itemView.findViewById<TextView>(R.id.patientUsernameTextView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val patientRowView = inflater.inflate(R.layout.row_patient_search_element, parent, false)
        return ViewHolder(patientRowView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rowModel = mPatientSearchRows[position]
        holder.nameTextView.text = rowModel.name
        holder.ageTextView.text = context.getString(R.string.age_patient_search, rowModel.age)
        holder.usernameTextView.text = rowModel.username

        holder.itemView.setOnClickListener {
            val activity = (context as FragmentActivity)
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    if (getToken(activity) == null)
                        throw java.lang.Exception("Unauthorized")

                    val invitationData = InvitationData()
                    invitationData.patientId = rowModel.id
                    invitationData.doctorUsername = getUsername(activity)
                    invitationData.isActive = true
                    val response = ResourceApiConntector.apiClient.createInvitation(getToken(activity)!!, invitationData)

                    if (response.isSuccessful) {
                        openNewFragment(activity, PatientsListFragment())
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

    override fun getItemCount(): Int {
        return mPatientSearchRows.size
    }
}