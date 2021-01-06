package com.edu.agh.easist.easistapp.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.ui.fragments.DiaryEntryDetailsFragment
import com.edu.agh.easist.easistapp.ui.models.ListRowModel
import com.edu.agh.easist.easistapp.ui.models.PatientRowModel

class ListRowAdapter(private val mListRows: List<ListRowModel>, private val editable: Boolean = true) : RecyclerView.Adapter<ListRowAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val textView = itemView.findViewById<TextView>(R.id.listRowText)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val medicineRowView = inflater.inflate(R.layout.row_list_element, parent, false)
        return ViewHolder(medicineRowView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rowModel = mListRows[position]
        holder.textView.text = rowModel.text

        if (editable) {
            holder.itemView.setOnLongClickListener {
                return@setOnLongClickListener true
            }
        }
    }

    override fun getItemCount(): Int {
        return mListRows.size
    }
}