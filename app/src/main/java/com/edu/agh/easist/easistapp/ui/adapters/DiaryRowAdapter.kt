package com.edu.agh.easist.easistapp.ui.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.ui.fragments.DiaryEntryDetailsFragment
import com.edu.agh.easist.easistapp.ui.models.DiaryRowModel
import com.edu.agh.easist.easistapp.utils.setImageView


class DiaryRowAdapter(private val mDiaryRows: List<DiaryRowModel>, private val context: Context) : RecyclerView.Adapter<DiaryRowAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val dateTextView = itemView.findViewById<TextView>(R.id.diaryEntryTitleEditText)!!
        val contentTextView = itemView.findViewById<TextView>(R.id.diaryEntryContentEditText)!!
        val sleepTimeTextView = itemView.findViewById<TextView>(R.id.diaryEntrySleepTimeTextView)!!
        val moodImageView = itemView.findViewById<ImageView>(R.id.diaryEntryImageView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val diaryRowView = inflater.inflate(R.layout.row_diary_element, parent, false)
        return ViewHolder(diaryRowView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rowModel = mDiaryRows[position]
        holder.dateTextView.text = rowModel.date
        holder.contentTextView.text = rowModel.content
        holder.sleepTimeTextView.text = context.getString(R.string.formatter__sleep_time, rowModel.sleepTime)
        setImageView(rowModel.mood, holder.moodImageView)

        holder.itemView.setOnClickListener{
            val fragment = DiaryEntryDetailsFragment()
            val ft: FragmentTransaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            val bundle = Bundle()
            bundle.putSerializable("diaryEntryId", rowModel.id)
            fragment.arguments = bundle
            ft.replace(R.id.container, fragment, fragment.javaClass.simpleName)
            ft.addToBackStack(null)
            ft.commit()
        }
    }

    override fun getItemCount(): Int {
        return mDiaryRows.size
    }
}