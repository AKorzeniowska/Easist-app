package com.edu.agh.easist.easistapp.utils

import android.app.TimePickerDialog
import android.content.Context
import android.content.res.Resources
import android.widget.EditText
import com.edu.agh.easist.easistapp.R
import java.util.*

fun setTimePicker(editText: EditText, context: Context){
    editText.setOnClickListener {
        val calendar: Calendar = Calendar.getInstance()
        val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes: Int = calendar.get(Calendar.MINUTE)
        val picker = TimePickerDialog(
                context,
                android.R.style.Theme_DeviceDefault_Dialog,
                { _, sHour, sMinute -> run{
                    val minuteDisplay = if (sMinute >= 10) "0$sMinute" else sMinute.toString()
                    val timeDisplay = Resources.getSystem().getString(R.string.formatter__time,
                            "$sHour", minuteDisplay)
                    editText.setText(timeDisplay)
                } }, hour, minutes, true
        )
        picker.show()
    }
}