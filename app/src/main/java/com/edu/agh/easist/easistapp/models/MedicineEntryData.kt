package com.edu.agh.easist.easistapp.models

import com.edu.agh.easist.easistapp.ui.models.MedicineRowModel
import com.edu.agh.easist.easistapp.ui.models.SymptomRowModel
import com.edu.agh.easist.easistapp.utils.getCurrentDate
import java.io.Serializable
import java.sql.Date

class MedicineEntryData  : Serializable {
    var taken: Boolean? = null
    var medicineId: Long? = null
    var date: Date? = null

    fun isValid(): Boolean{
        this.date = getCurrentDate()
        if (taken == null || medicineId == null)
            return false
        return true
    }

    constructor(rowModel: MedicineRowModel){
        taken = rowModel.taken
        medicineId = rowModel.medicineId
    }
}