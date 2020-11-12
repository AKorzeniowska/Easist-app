package com.edu.agh.easist.easistapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.ui.models.DiaryRowModel

class DiaryRowAdapter (private val mDiaryRows: List<DiaryRowModel>) : RecyclerView.Adapter<DiaryRowAdapter.ViewHolder>() {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val dateTextView = itemView.findViewById<TextView>(R.id.diaryEntryTitleEditText)!!
        val contentTextView = itemView.findViewById<TextView>(R.id.diaryEntryContentEditText)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val diaryRowView = inflater.inflate(R.layout.row_diary_element, parent, false)
        return ViewHolder(diaryRowView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rowModel = mDiaryRows[position]
        val textView = holder.dateTextView
        textView.text = rowModel.date
        holder.contentTextView.text = rowModel.content
    }

    override fun getItemCount(): Int {
        return mDiaryRows.size
    }
}