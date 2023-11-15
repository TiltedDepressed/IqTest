package com.example.iqtest.interfaces

import com.example.iqtest.model.ApiResponse
import com.example.iqtest.model.Question
import com.example.iqtest.model.User
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    //User
    @Headers("Content-Type:application/json")
    @POST("user/login")
    fun signIn(
        @Body body: JsonObject
    ): Call<User>

    @Headers("Content-Type:application/json")
    @POST("user/signup")
    fun signUp(
        @Body body: JsonObject
    ): Call<User>

    @Headers("Content-Type:application/json")
    @POST("user/{userId}")
     fun getUserById(
        @Path("userId") id: String,
        @Body body: JsonObject
    ): Call<User>

    @Headers("Content-Type:application/json")
    @POST("user/delete/{userId}")
    fun deleteUserById(
        @Path("userId") id: String,
        @Body body: JsonObject
    ): Call<User>

    @Headers("Content-Type:application/json")
    @POST("user/update/{userId}")
    fun updateUserById(
        @Path("userId") id : String,
        @Body body: JsonObject
    ): Call<User>

    @Headers("Content-Type:application/json")
    @POST("user/role/{role}")
    fun getAllUsersByRole(
        @Path("role") id : String,
        @Body body: JsonObject
    ): Call<ApiResponse<User>>

    //Question

    @Headers("Content-Type:application/json")
    @POST("question")
    fun createQuestion(
        @Body body: JsonObject
    ): Call<Question>
    @Headers("Content-Type:application/json")
    @POST("question/{questionId}")
    fun findQuestionById(
        @Path("questionId") id : String,
        @Body body: JsonObject
    ): Call<Question>

    @Headers("Content-Type:application/json")
    @POST("question/questions")
    fun getAllQuestions(
        @Body body: JsonObject
    ): Call<ApiResponse<Question>>

    @Headers("Content-Type:application/json")
    @POST("question/update/{questionId}")
    fun updateQuestionById(
        @Path("questionId") id: String,
        @Body body: JsonObject
    ): Call<Question>

    @Headers("Content-Type:application/json")
    @POST("question/delete/{questionId}")
    fun deleteQuestionById(
        @Path("questionId") id : String,
        @Body body: JsonObject
    ): Call<Question>

}