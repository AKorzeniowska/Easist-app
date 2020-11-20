package com.edu.agh.easist.easistapp.logic

import com.edu.agh.easist.easistapp.models.OAuthResponse
import com.edu.agh.easist.easistapp.models.UserData
import retrofit2.Response
import retrofit2.http.*


interface AuthApiClient {

    @POST("/auth/register")
    suspend fun register(
    @Body userData: UserData
    ): Response<Void>


    @POST("/oauth/token")
    suspend fun login(@Query("grant_type") grantType: String = "password",
                      @Query("username") username: String,
                      @Query("password") password: String): Response<OAuthResponse>

}