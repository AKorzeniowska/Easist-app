package com.edu.agh.easist.easistapp.utils

import android.app.ActionBar
import android.app.TimePickerDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.edu.agh.easist.easistapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_diary.*
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
                    val minuteDisplay = if (sMinute < 10) "0$sMinute" else sMinute.toString()
                    val hourDisplay = if (sHour < 10) "0$sHour" else sHour.toString()
                    val timeDisplay = context.getString(R.string.formatter__time,
                            hourDisplay, minuteDisplay)
                    editText.setText(timeDisplay)
                } }, hour, minutes, true
        )
        picker.show()
    }
}

fun showToast(context: Context?, textId: Int){
    Toast.makeText(context, textId, Toast.LENGTH_SHORT).show()
}

fun showToast(context: Context?, text: String){
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

fun showToolbar(activity: FragmentActivity, visible: Boolean){
    val toolbar = (activity as AppCompatActivity).supportActionBar
    if (visible){
        toolbar?.show()
    } else {
        toolbar?.hide()
    }
}

fun setToolbar(activity: FragmentActivity, title: String, subtitle: String?){
    val toolbar = (activity as AppCompatActivity).supportActionBar
    toolbar?.title = title
    if (!subtitle.isNullOrEmpty()){
        toolbar?.subtitle = subtitle
    }
}

fun showMenu(activity: FragmentActivity, visible: Boolean){
    if (visible) {
        activity.findViewById<View>(R.id.bottomNavigationView).visibility = View.VISIBLE
    }
    else
        activity.findViewById<View>(R.id.bottomNavigationView).visibility = View.GONE
}

fun showMenuItem(activity: FragmentActivity, itemId: Int, visible: Boolean){
    val menu = activity.findViewById<View>(R.id.bottomNavigationView) as BottomNavigationView
    val item = menu.menu.findItem(itemId)
    if (visible) {

    } else{
        item.icon = ColorDrawable(ContextCompat.getColor(activity, R.color.colorPrimary))
        item.isEnabled = false
    }
}

fun setImageView(mood: Int, view: ImageView){
    when (mood){
        0 -> view.setImageResource(R.drawable.ic_very_sad)
        1 -> view.setImageResource(R.drawable.ic_sad)
        2 -> view.setImageResource(R.drawable.ic_sceptic)
        3 -> view.setImageResource(R.drawable.ic_happy)
        4 -> view.setImageResource(R.drawable.ic_very_happy)
    }
}