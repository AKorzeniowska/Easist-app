package com.edu.agh.easist.easistapp.ui.fragments

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.ui.adapters.DiaryRowAdapter
import com.edu.agh.easist.easistapp.ui.adapters.SymptomRowAdapter
import com.edu.agh.easist.easistapp.ui.models.DiaryRowModel
import com.edu.agh.easist.easistapp.ui.models.SymptomRowModel
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import kotlin.collections.ArrayList


class DiaryFormFragmentFirst : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diary_form_first, container, false)

        val sleepTimeFromEditText = view.findViewById<EditText>(R.id.sleepTimeFromEditText)
        sleepTimeFromEditText.setOnClickListener(View.OnClickListener {
            val cldr: Calendar = Calendar.getInstance()
            val hour: Int = cldr.get(Calendar.HOUR_OF_DAY)
            val minutes: Int = cldr.get(Calendar.MINUTE)
            // time picker dialog
            val picker = TimePickerDialog(
                context,
                android.R.style.Theme_DeviceDefault_Dialog,
                { _, sHour, sMinute -> sleepTimeFromEditText.setText("$sHour:$sMinute") }, hour, minutes, true
            )
            picker.show()
        })

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}
