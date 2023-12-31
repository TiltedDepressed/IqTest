package com.example.iqtest.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iqtest.datasource.ServiceBuilder
import com.example.iqtest.interfaces.Api
import com.example.iqtest.model.Answer
import com.example.iqtest.model.ApiResponse
import com.example.iqtest.model.Question
import com.example.iqtest.model.User
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminViewModel: ViewModel() {

    private var userListLiveData = MutableLiveData<List<User>>()

    private var questionListLiveData = MutableLiveData<List<Question>>()

    private var userLiveData = MutableLiveData<User>()

    private var questionLiveData = MutableLiveData<Question>()

    private var answerLiveData = MutableLiveData<Answer>()

    private var answerListLiveData = MutableLiveData<List<Answer>>()
    fun getUsersByRole(role: String,data:JsonObject){
        val api = ServiceBuilder.buildService(Api::class.java)
        api.getAllUsersByRole(role,data).enqueue(object: Callback<ApiResponse<User>>{
            override fun onResponse(call: Call<ApiResponse<User>>, response: Response<ApiResponse<User>>) {
                if (response.isSuccessful) {
                    response.body()?.let { userList ->
                        Log.d("zxc",userList.toString())
                        userListLiveData.postValue(userList.result)
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse<User>>, t: Throwable) {
               Log.e("AdminViewModel", t.message.toString())
            }

        })
    }

    fun deleteUserById(userId: String, data: JsonObject) {
        val api = ServiceBuilder.buildService(Api::class.java)
        api.deleteUserById(userId, data).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("AdminViewModel", t.message.toString())
            }
        })
    }

    fun getInfoAboutUser(userId: String, data: JsonObject) {
        val api = ServiceBuilder.buildService(Api::class.java)
        api.getUserById(userId, data).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    response.body()?.let { user ->
                        userLiveData.postValue(user)
                    }
                }

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("AdminViewModel", t.message.toString())
            }

        })

    }

    fun changeUserData(userId: String, data: JsonObject) {
        val api = ServiceBuilder.buildService(Api::class.java)
        api.updateUserById(userId, data).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    observeUserLiveData()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("AdminViewModel", t.message.toString())
            }

        })
    }

    fun getAllQuestions(data: JsonObject){
        val api = ServiceBuilder.buildService(Api::class.java)
        api.getAllQuestions(data).enqueue(object : Callback<ApiResponse<Question>>{
            override fun onResponse(
                call: Call<ApiResponse<Question>>,
                response: Response<ApiResponse<Question>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { questionList ->
                        questionListLiveData.postValue(questionList.result)
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse<Question>>, t: Throwable) {
                Log.e("AdminViewModel", t.message.toString())
            }

        })
    }

    fun createQuestion(data: JsonObject){
        val api = ServiceBuilder.buildService(Api::class.java)
        api.createQuestion(data).enqueue(object : Callback<Question>{
            override fun onResponse(call: Call<Question>, response: Response<Question>) {

            }

            override fun onFailure(call: Call<Question>, t: Throwable) {
                Log.e("AdminViewModel", t.message.toString())
            }

        })
    }

    fun getQuestionById(id : String,data: JsonObject){
        val api = ServiceBuilder.buildService(Api::class.java)
        api.findQuestionById(id,data).enqueue(object : Callback<Question>{
            override fun onResponse(call: Call<Question>, response: Response<Question>) {
                if (response.isSuccessful) {
                    response.body()?.let { question ->
                        questionLiveData.postValue(question)

                    }
                }
            }

            override fun onFailure(call: Call<Question>, t: Throwable) {
                Log.e("AdminViewModel", t.message.toString())
            }

        })
    }

    fun updateQuestionById(id: String, data: JsonObject){
        val api = ServiceBuilder.buildService(Api::class.java)
        api.updateQuestionById(id,data).enqueue(object : Callback<Question>{
            override fun onResponse(call: Call<Question>, response: Response<Question>) {
                if(response.isSuccessful){
                    observeQuestionLiveData()
                }
            }

            override fun onFailure(call: Call<Question>, t: Throwable) {
                Log.e("AdminViewModel", t.message.toString())
            }

        })
    }

    fun deleteQuestionById(id: String, data:JsonObject){
        val api = ServiceBuilder.buildService(Api::class.java)
        api.deleteQuestionById(id,data).enqueue(object: Callback<Question>{
            override fun onResponse(call: Call<Question>, response: Response<Question>) {

            }
            override fun onFailure(call: Call<Question>, t: Throwable) {
                Log.e("AdminViewModel", t.message.toString())
            }

        })
    }

    fun getAnswersByQuestionId(id: String,data: JsonObject){
        val api = ServiceBuilder.buildService(Api::class.java)
        api.getAnswersByQuestionId(id,data).enqueue(object : Callback<ApiResponse<Answer>>{
            override fun onResponse(
                call: Call<ApiResponse<Answer>>,
                response: Response<ApiResponse<Answer>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { answerList ->
                        answerListLiveData.postValue(answerList.result)
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse<Answer>>, t: Throwable) {
                Log.e("AdminViewModel", t.message.toString())
            }

        })
    }

    fun createAnswerToQuestion(data: JsonObject){
        val api = ServiceBuilder.buildService(Api::class.java)
        api.createAnswerToQuestion(data).enqueue(object : Callback<Answer>{
            override fun onResponse(call: Call<Answer>, response: Response<Answer>) {

            }

            override fun onFailure(call: Call<Answer>, t: Throwable) {
                Log.e("AdminViewModel", t.message.toString())
            }

        })
    }

    fun getAllAnswers(data: JsonObject){
        val api = ServiceBuilder.buildService(Api::class.java)
        api.getAllAnswers(data).enqueue(object: Callback<ApiResponse<Answer>>{
            override fun onResponse(
                call: Call<ApiResponse<Answer>>,
                response: Response<ApiResponse<Answer>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { answerList ->
                        answerListLiveData.postValue(answerList.result)
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse<Answer>>, t: Throwable) {
                Log.e("AdminViewModel", t.message.toString())
            }

        })
    }

    fun deleteAnswerById(id: String,data: JsonObject){
        val api = ServiceBuilder.buildService(Api::class.java)
        api.deleteAnswerById(id,data).enqueue(object: Callback<Answer>{
            override fun onResponse(call: Call<Answer>, response: Response<Answer>) {

            }

            override fun onFailure(call: Call<Answer>, t: Throwable) {
                Log.e("AdminViewModel", t.message.toString())
            }

        })
    }

    fun getAnswerById(id: String, data: JsonObject){
        val api = ServiceBuilder.buildService(Api::class.java)
        api.getAnswerById(id,data).enqueue(object :Callback<Answer>{
            override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                if (response.isSuccessful) {
                    response.body()?.let { answer ->
                        answerLiveData.postValue(answer)
                    }
                }
            }

            override fun onFailure(call: Call<Answer>, t: Throwable) {
                Log.e("AdminViewModel", t.message.toString())
            }

        })
    }

    fun updateAnswerById(id: String, data:JsonObject){
        val api = ServiceBuilder.buildService(Api::class.java)
        api.updateAnswerById(id,data).enqueue(object: Callback<Answer>{
            override fun onResponse(call: Call<Answer>, response: Response<Answer>) {
                if(response.isSuccessful){
                    observeAnswerLiveData()
                }
            }

            override fun onFailure(call: Call<Answer>, t: Throwable) {
                Log.e("AdminViewModel", t.message.toString())
            }

        })
    }




    fun observeAnswerLiveData(): LiveData<Answer>{
        return answerLiveData
    }
    fun observeAnswerListLiveData(): LiveData<List<Answer>>{
        return answerListLiveData
    }
    fun observeUserLiveData(): LiveData<User>{
        return userLiveData
    }

    fun observeQuestionLiveData(): LiveData<Question>{
        return questionLiveData
    }

    fun observerQuestionListLiveData(): LiveData<List<Question>>{
        return questionListLiveData
    }

    fun observerUserListLiveData(): LiveData<List<User>> {
        return userListLiveData
    }



}