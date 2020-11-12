package com.edu.agh.easist.easistapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.ui.models.SymptomRowModel
import com.google.android.material.slider.Slider

class SymptomRowAdapter (private val mSymptomRows: List<SymptomRowModel>) : RecyclerView.Adapter<SymptomRowAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.symptomNameText)!!
        val symptomSlider = itemView.findViewById<Slider>(R.id.symptomSlider)!!

//        init {
//            symptomSlider.setLabelFormatter { value: Float ->
//                val format = NumberFormat.getCurrencyInstance()
//                format.maximumFractionDigits = 0
//                format.currency = Currency.getInstance("USD")
//                format.format(value.toDouble())
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val diaryRowView = inflater.inflate(R.layout.row_symptom_element, parent, false)
        return ViewHolder(diaryRowView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rowModel = mSymptomRows[position]
        holder.nameTextView.text = rowModel.name
        holder.symptomSlider.value = 0f
    }

    override fun getItemCount(): Int {
        return mSymptomRows.size
    }
}