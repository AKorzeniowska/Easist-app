package com.edu.agh.easist.easistapp.models

import com.edu.agh.easist.easistapp.utils.getCurrentDate
import timber.log.Timber
import java.io.Serializable
import java.sql.Date

class DiaryEntryData : Serializable {
    var date: Date? = null
    var content: String? = null
    var mood: Int? = null
    var sleepEntry: SleepEntryData? = null
    var symptomEntries: List<SymptomEntryData>? = null
    var medicineEntries: List<MedicineEntryData>? = null

    private fun setDate(){
//        val date = getCurrentDate()
//        this.date = parseDateToString(date)
        this.date = getCurrentDate()
        Timber.d(this.toString())
    }

    fun isFirstPartValid(): Boolean{
        if (date == null){
            setDate()
        }
        if (content.isNullOrBlank() || mood == null)
            return false
        if (sleepEntry == null || !sleepEntry!!.isValid())
            return false
        return true
    }

    fun isSecondPartValid(): Boolean{
        if (!isFirstPartValid())
            return false
        if (symptomEntries.isNullOrEmpty() || symptomEntries!!.any{ x -> !x.isValid() })
            return false
        return true
    }

    fun isThirdPartValid(): Boolean{
        if (!isSecondPartValid())
            return false
        if (medicineEntries.isNullOrEmpty() || medicineEntries!!.any{ x -> !x.isValid() })
            return false
        return true
    }

    override fun toString(): String {
        return "DiaryEntryData(date=$date, content=$content, mood=$mood, sleepEntry=$sleepEntry, symptomEntries=$symptomEntries, medicineEntries=$medicineEntries)"
    }
}