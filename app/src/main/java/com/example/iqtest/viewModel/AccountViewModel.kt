package com.example.iqtest.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iqtest.datasource.ServiceBuilder
import com.example.iqtest.interfaces.Api
import com.example.iqtest.model.User
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountViewModel: ViewModel() {
    private var userLiveData = MutableLiveData<User>()


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



    fun observerUserLiveData(): LiveData<User> {
        return userLiveData
    }

}
