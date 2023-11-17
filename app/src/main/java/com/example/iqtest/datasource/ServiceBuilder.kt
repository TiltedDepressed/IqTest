package com.example.iqtest.datasource

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://a47a-77-223-82-224.ngrok.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}