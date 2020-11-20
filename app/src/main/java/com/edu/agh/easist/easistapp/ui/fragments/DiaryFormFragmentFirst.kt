package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.models.DiaryEntryData
import com.edu.agh.easist.easistapp.models.SleepEntryData
import com.edu.agh.easist.easistapp.utils.openNewFragmentWithData
import com.edu.agh.easist.easistapp.utils.parseStringToTime
import com.edu.agh.easist.easistapp.utils.setTimePicker
import kotlinx.android.synthetic.main.fragment_diary_form_first.*


class DiaryFormFragmentFirst : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diary_form_first, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        setTimePickers()
        setButtonFunctions()
    }

    private fun setButtonFunctions(){
        continueDiaryEntryButton.setOnClickListener{
            val diaryEntry: DiaryEntryData? = submitDiaryEntry()
            if (diaryEntry != null){
                openNewFragmentWithData(activity, DiaryFormFragmentSecond(),
                        "diaryEntry", diaryEntry)
            } else {
                Toast.makeText(context, R.string.warning__invalid_data, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setTimePickers(){
        setTimePicker(sleepTimeFromEditText, requireContext())
        setTimePicker(sleepTimeToEditText, requireContext())
    }

    private fun submitDiaryEntry(): DiaryEntryData?{
        val diaryEntryData = DiaryEntryData()
        val sleepEntryData = SleepEntryData()

        val radioButtonID: Int = moodRadioGroup.checkedRadioButtonId
        val radioButton: View = moodRadioGroup.findViewById(radioButtonID)
        diaryEntryData.mood = moodRadioGroup.indexOfChild(radioButton)

        sleepEntryData.timeFrom = parseStringToTime(sleepTimeFromEditText.text.toString())
        sleepEntryData.timeTo = parseStringToTime(sleepTimeToEditText.text.toString())
        diaryEntryData.sleepEntry = sleepEntryData

        diaryEntryData.content = diaryEntryEditText.text.toString()

        return if (diaryEntryData.isFirstPartValid()) diaryEntryData else null
    }
}
