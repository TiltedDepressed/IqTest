package com.example.iqtest.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.iqtest.R
import com.example.iqtest.databinding.ActivityRegistrationBinding
import com.example.iqtest.datasource.ServiceBuilder
import com.example.iqtest.interfaces.Api
import com.example.iqtest.model.User
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInButton.setOnClickListener {
            val intent = Intent(this@RegistrationActivity,AuthActivity::class.java)
            startActivity(intent)
        }

        binding.registrationButton.setOnClickListener {

            val login = binding.loginEt.text.toString()

            val email = binding.emailEt.text.toString()

            val password = binding.passwordEt.text.toString()

            registration(login,email,password)
        }

    }

    private fun registration(login: String, email: String, password: String) {
        
        if(binding.agreeTermsCheckBox.isChecked){
            val data = JsonObject()

            data.addProperty("login",login)
            data.addProperty("email", email)
            data.addProperty("password", password)

            val api = ServiceBuilder.buildService(Api::class.java)
            api.signUp(data).enqueue(object:Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.isSuccessful){
                        val intent = Intent(this@RegistrationActivity,AuthActivity::class.java)
                        startActivity(intent)
                    } else{
                        Toast.makeText(this@RegistrationActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("RegistrationActivity",t.message.toString())
                }

            })
        } else{
            Toast.makeText(this, "You should be agree with terms and conditions", Toast.LENGTH_SHORT).show()
        }

  
    }
}