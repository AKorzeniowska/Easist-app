package com.edu.agh.easist.easistapp.models

import com.edu.agh.easist.easistapp.ui.models.SymptomRowModel
import com.edu.agh.easist.easistapp.utils.getCurrentDate
import java.io.Serializable
import java.sql.Date

class SymptomEntryData : Serializable{
    var intensity: Int? = null
    var comment: String? = null
    var symptomId: Long? = null
    var date: Date? = null

    fun isValid(): Boolean{
        this.date = getCurrentDate()
        if (intensity == null || symptomId == null)
            return false
        return true
    }

    constructor(rowModel: SymptomRowModel){
        intensity = rowModel.intensity
        symptomId = rowModel.symptomId
    }
}