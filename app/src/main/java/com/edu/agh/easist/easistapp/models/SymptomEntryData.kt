package com.edu.agh.easist.easistapp.models

import java.io.Serializable

class SymptomEntryData : Serializable{
    var intensity: Int? = null
    var comment: String? = null
    var symptom: Symptom? = null

    fun isValid(): Boolean{
        if (intensity == null || symptom == null)
            return false
        return true
    }
}