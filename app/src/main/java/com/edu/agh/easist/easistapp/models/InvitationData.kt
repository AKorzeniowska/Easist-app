package com.edu.agh.easist.easistapp.models

import java.io.Serializable

class InvitationData : Serializable {
    var id: Long? = null
    var patientId: Long? = null
    var patientUsername: String? = null
    var doctorId: Long? = null
    var doctorUsername: String? = null
    var isActive: Boolean? = null
    var gotAccepted: Boolean? = null

    fun isValid(): Boolean{
        if (patientId == null && patientUsername == null)
            return false
        if (doctorUsername == null && doctorId == null)
            return false
        if (isActive == null)
            return false
        return true
    }

    constructor()

    constructor(invitation: Invitation){
        this.id = invitation.id
        this.patientId = invitation.patient!!.id
        this.doctorId = invitation.doctor!!.id
        this.patientUsername = invitation.patient.username
        this.doctorUsername = invitation.doctor.username
    }
}