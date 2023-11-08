package com.example.iqtest.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.iqtest.R
import com.example.iqtest.databinding.ActivityAuthBinding
import com.example.iqtest.datasource.ServiceBuilder
import com.example.iqtest.interfaces.Api
import com.example.iqtest.model.User
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var sharePreference: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInButton.setOnClickListener {

            val login = binding.loginEt.text.toString()

            val password = binding.passwordEt.text.toString()

            signIn(login,password)

        }
            autoLogIn()

        binding.registrationButton.setOnClickListener {
            val intent = Intent(this@AuthActivity,RegistrationActivity::class.java)
            startActivity(intent)
        }

    }

    private fun autoLogIn() {
        sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        if(sharePreference.getString("TOKEN", null) != null){
            val intent = Intent(this@AuthActivity, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "auto-log-in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signIn(login: String, password:String){

        sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharePreference.edit()
        val data = JsonObject()

        data.addProperty("login", login)
        data.addProperty("password",password)

        val api = ServiceBuilder.buildService(Api::class.java)
        api.signIn(data).enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d("AuthViewModel",data.toString())
                if(response.isSuccessful){

                    response.body()?.let {  userList ->

                        editor.putString("USER_ID",userList.userId)
                        editor.apply()
                        if(binding.rememberMeCheckBox.isChecked) {
                            editor.putString("TOKEN", userList.token)
                            editor.apply()
                        }

                    }

                    val intent = Intent(this@AuthActivity,MainActivity::class.java)
                    startActivity(intent)
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("AuthActivity", t.message.toString())
            }

        })
    }

}