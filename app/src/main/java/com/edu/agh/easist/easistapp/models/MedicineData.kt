package com.edu.agh.easist.easistapp.models


class MedicineData {
    var name: String? = null
    var dose: String? = null

    fun isValid(): Boolean{
        if (name.isNullOrBlank())
            return false
        return true
    }
}