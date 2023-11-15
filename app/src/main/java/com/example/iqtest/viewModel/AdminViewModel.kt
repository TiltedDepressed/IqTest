package com.example.iqtest.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iqtest.datasource.ServiceBuilder
import com.example.iqtest.interfaces.Api
import com.example.iqtest.model.ApiResponse
import com.example.iqtest.model.User
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminViewModel: ViewModel() {

    private var userLiveData = MutableLiveData<List<User>>()
    fun getUsersByRole(role: String,data:JsonObject){
        val api = ServiceBuilder.buildService(Api::class.java)
        api.getAllUsersByRole(role,data).enqueue(object: Callback<ApiResponse<User>>{
            override fun onResponse(call: Call<ApiResponse<User>>, response: Response<ApiResponse<User>>) {
                if (response.isSuccessful) {
                    response.body()?.let { userList ->
                        Log.d("zxc",userList.toString())
                        userLiveData.postValue(userList.result)
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse<User>>, t: Throwable) {
               Log.e("AdminViewModel", t.message.toString())
            }

        })
    }

    fun observerUserLiveData(): LiveData<List<User>> {
        return userLiveData
    }



}