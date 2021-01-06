package com.edu.agh.easist.easistapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.ui.models.MedicineRowModel

class MedicineRowAdapter(private val mMedicineRows: List<MedicineRowModel>, private val editable: Boolean = true) : RecyclerView.Adapter<MedicineRowAdapter.ViewHolder>() {

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.medicineNameText)!!
        val medicineCheckBox = itemView.findViewById<CheckBox>(R.id.medicineCheckBox)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val medicineRowView = inflater.inflate(R.layout.row_medicine_element, parent, false)
        return ViewHolder(medicineRowView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rowModel = mMedicineRows[position]
        holder.nameTextView.text = rowModel.name
        holder.medicineCheckBox.isChecked = rowModel.taken

        if (editable) {
            holder.medicineCheckBox.setOnCheckedChangeListener { _, isChecked ->
                rowModel.taken = isChecked
            }
        } else {
            holder.medicineCheckBox.isEnabled = false
        }
    }

    override fun getItemCount(): Int {
        return mMedicineRows.size
    }
}