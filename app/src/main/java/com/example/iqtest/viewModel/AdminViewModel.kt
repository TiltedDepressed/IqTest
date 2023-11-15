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

    private var userListLiveData = MutableLiveData<List<User>>()

    private var userLiveData = MutableLiveData<User>()
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
                Log.e("AccountFragment", t.message.toString())
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
                Log.e("AccountViewModel", t.message.toString())
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
                Log.e("AccountFragment", t.message.toString())
            }

        })
    }



    fun observeUserLiveData(): LiveData<User>{
        return userLiveData
    }



    fun observerUserListLiveData(): LiveData<List<User>> {
        return userListLiveData
    }



}