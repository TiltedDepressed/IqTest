package com.example.iqtest.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
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
    private var rememberMe: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInButton.setOnClickListener {

            if(binding.loginEt.text.isNotEmpty()){
                binding.loginEt.background = resources.getDrawable(R.drawable.edit_text_background)
                binding.wrongDataTextView.visibility = View.GONE

            }

            if(binding.passwordEt.text.isNotEmpty()){
                binding.passwordEt.background = resources.getDrawable(R.drawable.edit_text_background)
                binding.wrongPasswordTextView.visibility = View.GONE

            }

            if(binding.loginEt.text.isEmpty()){
                binding.wrongDataTextView.text = "Login cannot be empty"
                binding.wrongDataTextView.visibility = View.VISIBLE
                binding.loginEt.background = resources.getDrawable(R.drawable.edit_text_background_red)
            }

            if (binding.passwordEt.text.isEmpty()){
                binding.wrongPasswordTextView.text = "Password cannot be empty"
                binding.wrongPasswordTextView.visibility = View.VISIBLE
                binding.passwordEt.background = resources.getDrawable(R.drawable.edit_text_background_red)
            }




            val login = binding.loginEt.text.toString()

            val password = binding.passwordEt.text.toString()

            if(login.isNotEmpty() && password.isNotEmpty()){
                signIn(login,password)
            }

        }
            autoLogIn()

        binding.registrationButton.setOnClickListener {
            val intent = Intent(this@AuthActivity,RegistrationActivity::class.java)
            startActivity(intent)
        }

    }

    private fun autoLogIn() {
        sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        if(sharePreference.getBoolean("REMEMBER_ME", false)){
            val intent = Intent(this@AuthActivity, MainActivity::class.java)
            startActivity(intent)
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

                    response.body()?.let {userList ->

                        editor.putString("USER_ID",userList.userId)
                        editor.putString("TOKEN", userList.token)
                        editor.apply()
                        if(binding.rememberMeCheckBox.isChecked) {
                             rememberMe = true
                            editor.putBoolean("REMEMBER_ME", rememberMe!!)
                            editor.apply()
                        }

                    }
                    binding.signInButton.isEnabled = false
                    val intent = Intent(this@AuthActivity,MainActivity::class.java)
                    startActivity(intent)
                }
                if(!response.isSuccessful){
                    binding.wrongDataTextView.visibility = View.VISIBLE
                    binding.wrongDataTextView.text = "Wrong login or password try again"
                    binding.loginEt.background = resources.getDrawable(R.drawable.edit_text_background_red);
                    binding.passwordEt.background = resources.getDrawable(R.drawable.edit_text_background_red);
                }

            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("AuthActivity", t.message.toString())
            }

        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }




}

private abstract class  SimpleTextWatcher : TextWatcher{
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

    override fun afterTextChanged(s: Editable?) = Unit



}