package com.example.iqtest.interfaces

import com.example.iqtest.model.User
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    @Headers("Content-Type:application/json")
    @POST("user/login")
    fun signIn(
        @Body body: JsonObject
    ): Call<User>

}