package com.edu.agh.easist.easistapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Date
import java.util.*

class DiaryEntryData : Serializable {
    var date: Date? = null
    var content: String? = null
    var mood: Int? = null
    var sleepEntry: SleepEntryData? = null
    var symptomEntries: List<SymptomEntryData>? = null

    private fun setDate(){
        date = Date(Calendar.getInstance().time.time)
    }

    fun isFirstPartValid(): Boolean{
        if (date == null){
            setDate()
        }

        if (content.isNullOrBlank() || mood == null)
            return false
        if (sleepEntry == null || !sleepEntry!!.isValid())
            return false
        if (symptomEntries.isNullOrEmpty() || symptomEntries!!.any{ x -> !x.isValid() })
            return false

        return true
    }
}