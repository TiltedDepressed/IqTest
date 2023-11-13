package com.example.iqtest.activities

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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

            if(binding.loginEt.text.isEmpty()){
                binding.wrongLoginTextView.visibility = View.VISIBLE
                binding.loginEt.background = resources.getDrawable(R.drawable.edit_text_background_red)
            } else{
                binding.wrongLoginTextView.visibility = View.GONE
                binding.loginEt.background = resources.getDrawable(R.drawable.edit_text_background)
            }
            if(binding.emailEt.text.isEmpty()){
                binding.wrongEmailTextView.visibility = View.VISIBLE
                binding.emailEt.background = resources.getDrawable(R.drawable.edit_text_background_red)
            }else{
                binding.wrongEmailTextView.visibility = View.GONE
                binding.emailEt.background = resources.getDrawable(R.drawable.edit_text_background)
            }
            if(binding.passwordEt.text.isEmpty()){
                binding.wrongPasswordTextView.visibility = View.VISIBLE
                binding.passwordEt.background = resources.getDrawable(R.drawable.edit_text_background_red)
            }else{
                binding.wrongPasswordTextView.visibility = View.GONE
                binding.passwordEt.background = resources.getDrawable(R.drawable.edit_text_background)
            }

            val login = binding.loginEt.text.toString()

            val email = binding.emailEt.text.toString()

            val password = binding.passwordEt.text.toString()

            if(login.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
                registration(login,email,password)
            }

            return@setOnClickListener
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
                    }
                    if(response.code() == 409){
                        binding.userAlreadyExistTextView.visibility = View.VISIBLE
                        binding.loginEt.background = resources.getDrawable(R.drawable.edit_text_background_red)
                        binding.emailEt.background = resources.getDrawable(R.drawable.edit_text_background_red)
                        binding.passwordEt.background = resources.getDrawable(R.drawable.edit_text_background_red)
                    }else{
                        binding.userAlreadyExistTextView.visibility = View.GONE
                        binding.loginEt.background = resources.getDrawable(R.drawable.edit_text_background)
                        binding.emailEt.background = resources.getDrawable(R.drawable.edit_text_background)
                        binding.passwordEt.background = resources.getDrawable(R.drawable.edit_text_background)
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("RegistrationActivity",t.message.toString())
                }

            })
        } else{
            binding.checkboxLayout.background = resources.getDrawable(R.drawable.edit_text_background_red)
            binding.checkBoxDoesNotCheckedTextView.visibility = View.VISIBLE
        }

  
    }
}