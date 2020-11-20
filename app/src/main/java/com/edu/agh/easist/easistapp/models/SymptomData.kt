package com.edu.agh.easist.easistapp.models

class SymptomData {
    var name: String? = null
    var comment: String? = null

    fun isValid(): Boolean{
        if (name.isNullOrBlank())
            return false
        return true
    }
}