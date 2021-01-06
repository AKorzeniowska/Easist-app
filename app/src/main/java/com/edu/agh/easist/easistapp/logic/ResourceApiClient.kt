package com.edu.agh.easist.easistapp.logic

import com.edu.agh.easist.easistapp.models.*
import retrofit2.Response
import retrofit2.http.*

interface ResourceApiClient {
    @GET("/diary/{username}")
    suspend fun getDiaryEntries(@Header("Authorization") authHeader: String,
                                @Path("username") username: String): Response<Collection<DiaryEntry>>

    @POST("/diary/entry")
    suspend fun addDiaryEntry(@Header("Authorization") authHeader: String,
                              @Body entry: DiaryEntryData): Response<Void>

    @PUT("/diary/entry")
    suspend fun updateDiaryEntry(@Header("Authorization") authHeader: String,
                              @Body entry: DiaryEntryData): Response<Void>

    @POST("/symptom")
    suspend fun addSymptom(@Header("Authorization") authHeader: String,
                           @Body symptom: SymptomData): Response<Void>

    @GET("/symptom/{username}")
    suspend fun getSymptoms(@Header("Authorization") authHeader: String,
                            @Path("username") username: String): Response<Collection<Symptom>>

    @GET("/patient/{username}")
    suspend fun getPatient(@Header("Authorization") authHeader: String,
                           @Path("username") username: String): Response<Patient>

    @GET("/symptomEntry/{username}/symptomEntries")
    suspend fun getSymptomEntriesByDate(@Header("Authorization") authHeader: String,
                                         @Path("username") username: String): Response<Map<String, Collection<SymptomEntry>>>

    @GET("/diary/sleepEntries/{username}")
    suspend fun getSleepEntries(@Header("Authorization") authHeader: String,
                                        @Path("username") username: String): Response<Collection<SleepEntry>>

    @POST("/medicine")
    suspend fun addMedicine(@Header("Authorization") authHeader: String,
                           @Body medicine: MedicineData): Response<Void>

    @GET("/medicine/{username}")
    suspend fun getMedicines(@Header("Authorization") authHeader: String,
                            @Path("username") username: String): Response<Collection<Medicine>>

    @GET("/diary/{username}/{id}")
    suspend fun getDiaryEntryById(@Header("Authorization") authHeader: String,
                                  @Path("username") username: String, @Path("id") id: Long): Response<DiaryEntry>

    @GET("/patient/doctor")
    suspend fun getPatientsDoctor(@Header("Authorization") authHeader: String): Response<Doctor>

    @GET("/doctor")
    suspend fun getDoctor(@Header("Authorization") authHeader: String): Response<Doctor>

    @GET("/doctor/patients")
    suspend fun getDoctorsPatients(@Header("Authorization") authHeader: String): Response<Collection<Patient>>

    @POST("/invitation")
    suspend fun createInvitation(@Header("Authorization") authHeader: String, @Body invitation: InvitationData): Response<Void>

    @PUT("/invitation")
    suspend fun updateInvitation(@Header("Authorization") authHeader: String, @Body invitation: InvitationData): Response<Void>

    @GET("/patient/search/{text}")
    suspend fun getPatientsByUsernameOrLastName(@Header("Authorization") authHeader: String,
                                                @Path("text") text: String): Response<Collection<Patient>>

    @GET("/invitation/patients")
    suspend fun getPatientsFromActiveInvitations(@Header("Authorization") authHeader: String): Response<Collection<Patient>>

    @GET("/invitation/active")
    suspend fun getActiveInvitation(@Header("Authorization") authHeader: String): Response<Invitation>

    @DELETE("/medicine/{id}")
    suspend fun deleteMedicine(@Header("Authorization") authHeader: String,
                               @Path("id") id: Long): Response<Void>

    @DELETE("/symptom/{id}")
    suspend fun deleteSymptom(@Header("Authorization") authHeader: String,
                               @Path("id") id: Long): Response<Void>
}