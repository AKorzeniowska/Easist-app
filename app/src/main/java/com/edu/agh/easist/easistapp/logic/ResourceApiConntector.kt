package com.edu.agh.easist.easistapp.logic

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ResourceApiConntector {

    private const val BASE_URL = "http://192.168.0.101:8080/"
//    private const val BASE_URL = "http://10.0.0.2:8080/"

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val loggingInterceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val client = OkHttpClient.Builder()
//        .addInterceptor(BasicAuthInterceptor("fooClientId", "secret"))
        .addInterceptor(loggingInterceptor)
        .build()

    val apiClient: ResourceApiClient = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ResourceApiClient::class.java)
}