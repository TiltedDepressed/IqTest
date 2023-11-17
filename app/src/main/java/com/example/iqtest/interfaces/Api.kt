package com.example.iqtest.interfaces

import com.example.iqtest.model.Answer
import com.example.iqtest.model.ApiResponse
import com.example.iqtest.model.Question
import com.example.iqtest.model.User
import com.example.iqtest.model.Result
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

    //Answer
    @Headers("Content-Type:application/json")
    @POST("answer/find/{questionId}")
    fun getAnswersByQuestionId(
        @Path("questionId") id: String,
        @Body body : JsonObject
    ): Call<ApiResponse<Answer>>

    @Headers("Content-Type:application/json")
    @POST("answer/create")
    fun createAnswerToQuestion(
        @Body body : JsonObject
    ): Call<Answer>

    @Headers("Content-Type:application/json")
    @POST("answer")
    fun getAllAnswers(
        @Body body: JsonObject
    ): Call<ApiResponse<Answer>>

    @Headers("Content-Type:application/json")
    @POST("answer/delete/{answerId}")
    fun deleteAnswerById(
        @Path("answerId") id : String,
        @Body body: JsonObject
    ): Call<Answer>

    @Headers("Content-Type:application/json")
    @POST("answer/{answerId}")
    fun getAnswerById(
        @Path("answerId") id : String,
        @Body body: JsonObject
    ): Call<Answer>

    @Headers("Content-Type:application/json")
    @POST("answer/update/{answerId}")
    fun updateAnswerById(
        @Path("answerId") id: String,
        @Body body: JsonObject
    ): Call<Answer>

    @Headers("Content-Type:application/json")
    @POST("question/questions/random/{count}")
    fun generateTest(
        @Path("count") count : String,
        @Body body: JsonObject
    ): Call<ApiResponse<Question>>

    @Headers("Content-Type:application/json")
    @POST("result/create")
    fun createTestResult(
        @Body body: JsonObject
    ): Call<Result>

    @Headers("Content-Type:application/json")
    @POST("result/find/{userId}")
    fun getResultByUserId(
        @Path("userId") id : String,
        @Body body : JsonObject
    ): Call<ApiResponse<Result>>
}