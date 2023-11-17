package com.example.iqtest.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iqtest.datasource.ServiceBuilder
import com.example.iqtest.interfaces.Api
import com.example.iqtest.model.ApiResponse
import com.example.iqtest.model.Result
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel: ViewModel() {
    private var resultListLiveData = MutableLiveData<List<Result>>()

   fun getResultsByUserId(id: String,data: JsonObject){
       val api = ServiceBuilder.buildService(Api::class.java)
       api.getResultByUserId(id,data).enqueue(object: Callback<ApiResponse<Result>>{
           override fun onResponse(
               call: Call<ApiResponse<Result>>,
               response: Response<ApiResponse<Result>>
           ) {
               if(response.isSuccessful){
                   response.body()?.let { resultList ->
                       resultListLiveData.postValue(resultList.result)
                   }
               }
           }

           override fun onFailure(call: Call<ApiResponse<Result>>, t: Throwable) {
              Log.e("HistoryViewModel",t.message.toString())
           }

       })
   }




    fun observerResultListLiveData(): LiveData<List<Result>> {
        return resultListLiveData
    }

}
