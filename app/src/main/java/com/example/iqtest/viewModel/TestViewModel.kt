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
import com.example.iqtest.model.Result
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestViewModel(): ViewModel() {

    private var questionListLiveData = MutableLiveData<List<Question>>()

    private var questionLiveData = MutableLiveData<Question>()

    private var answerLiveData = MutableLiveData<Answer>()

    private var answerListLiveData = MutableLiveData<List<Answer>>()


    fun generateTest(count: String, data: JsonObject){
        val api = ServiceBuilder.buildService(Api::class.java)
        api.generateTest(count,data).enqueue(object: Callback<ApiResponse<Question>>{
            override fun onResponse(
                call: Call<ApiResponse<Question>>,
                response: Response<ApiResponse<Question>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {questionList ->
                        questionListLiveData.postValue(questionList.result)
                    }

                }
            }

            override fun onFailure(call: Call<ApiResponse<Question>>, t: Throwable) {
               Log.e("TestViewModel", t.message.toString())
            }

        })
    }

    fun createTestResult(data: JsonObject){
        val api = ServiceBuilder.buildService(Api::class.java)
        api.createTestResult(data).enqueue(object: Callback<Result>{
            override fun onResponse(call: Call<Result>, response: Response<Result>) {

            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                Log.e("TestViewModel", t.message.toString())
            }

        })
    }



    fun observeAnswerLiveData(): LiveData<Answer>{
        return answerLiveData
    }
    fun observeAnswerListLiveData(): LiveData<List<Answer>>{
        return answerListLiveData
    }

    fun observeQuestionLiveData(): LiveData<Question> {
        return questionLiveData
    }

    fun observerQuestionListLiveData(): LiveData<List<Question>> {
        return questionListLiveData
    }


}