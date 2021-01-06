package com.edu.agh.easist.easistapp.logic

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RoleApiClient {

    @GET("/auth/role")
    suspend fun getRole(@Header("Authorization") authHeader: String): Response<String>
}