package com.edu.agh.easist.easistapp.logic

import com.edu.agh.easist.easistapp.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ResourceApiClient {
    @GET("/diary/all")
    suspend fun getAll(@Header("Authorization") authHeader: String): Response<Collection<DiaryEntry>>

    @POST("/diary/add")
    suspend fun addDiaryEntry(@Header("Authorization") authHeader: String, @Body entry: DiaryEntryData): Response<Void>

    @POST("/symptom/add")
    suspend fun addSymptom(@Header("Authorization") authHeader: String, @Body symptom: SymptomData): Response<Void>
}