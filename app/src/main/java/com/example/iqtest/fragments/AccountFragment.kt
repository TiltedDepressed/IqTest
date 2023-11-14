package com.example.iqtest.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.iqtest.activities.AuthActivity
import com.example.iqtest.activities.TestActivity
import com.example.iqtest.databinding.FragmentAccountBinding
import com.example.iqtest.datasource.ServiceBuilder
import com.example.iqtest.interfaces.Api
import com.example.iqtest.model.User
import com.example.iqtest.viewModel.AccountViewModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding

    private lateinit var sharePreference: SharedPreferences

    private lateinit var accViewModel: AccountViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharePreference = this.requireActivity().getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        accViewModel = ViewModelProvider(this)[AccountViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUserLiveData()

        binding.logOut.setOnClickListener {
            val editor: SharedPreferences.Editor = sharePreference.edit()
            editor.clear()
            editor.apply()
            val intent = Intent(this.requireActivity(), AuthActivity::class.java)
            startActivity(intent)
        }

        binding.deleteAccountBtn.setOnClickListener {

            val data = JsonObject()
            val userId = sharePreference.getString("USER_ID",null).toString()
            data.addProperty("token", sharePreference.getString("TOKEN",null))

            deleteUserById(userId,data)

            val editor: SharedPreferences.Editor = sharePreference.edit()
            editor.clear()
            editor.apply()
            val intent = Intent(this.requireActivity(), AuthActivity::class.java)
            startActivity(intent)
        }

        binding.changeUserNameButton.setOnClickListener {
            if(binding.loginEt.text.isEmpty()){
                Toast.makeText(context, "Не может быть пустым", Toast.LENGTH_SHORT).show()
            } else{
                val data = JsonObject()
                val userId = sharePreference.getString("USER_ID",null).toString()
                data.addProperty("token", sharePreference.getString("TOKEN",null))
                data.addProperty("login",binding.loginEt.text.toString())
                changeUserData(userId, data)
                binding.loginEt.text.clear()
            }
        }

        binding.changeEmailButton.setOnClickListener {
            if(binding.emailEt.text.isEmpty()){
                Toast.makeText(context, "Не может быть пустым", Toast.LENGTH_SHORT).show()
            } else{
                val data = JsonObject()
                val userId = sharePreference.getString("USER_ID",null).toString()
                data.addProperty("token", sharePreference.getString("TOKEN",null))
                data.addProperty("email",binding.emailEt.text.toString())
                changeUserData(userId, data)
                binding.emailEt.text.clear()
            }
        }

        binding.changePasswordButton.setOnClickListener {
            if(binding.passwordEt.text.isEmpty()){
                Toast.makeText(context, "Не может быть пустым", Toast.LENGTH_SHORT).show()
            } else{
                val data = JsonObject()
                val userId = sharePreference.getString("USER_ID",null).toString()
                data.addProperty("token", sharePreference.getString("TOKEN",null))
                data.addProperty("password",binding.passwordEt.text.toString())
                changeUserData(userId, data)
                binding.passwordEt.text.clear()
            }
        }

        adminButtonView()


    }

    private fun adminButtonView() {
      val userRole = sharePreference.getString("ROLE",null)
        if(userRole == "2"){
            binding.adminButton.visibility = View.VISIBLE
        }
        binding.adminButton.setOnClickListener {
            val intent = Intent(this.requireActivity(),TestActivity::class.java)
            startActivity(intent)
        }
    }

    private fun changeUserData(userId: String,data: JsonObject) {
        val api = ServiceBuilder.buildService(Api::class.java)
        api.updateUserById(userId,data).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                   if(response.isSuccessful){
                       observeUserLiveData()
                   }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("AccountFragment", t.message.toString())
            }

        })
    }

    private fun deleteUserById(userId: String,data: JsonObject) {
        val api = ServiceBuilder.buildService(Api::class.java)
        api.deleteUserById(userId,data).enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {

            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("AccountFragment", t.message.toString())
            }
        })
    }

    private fun observeUserLiveData() {

        val data = JsonObject()
        val userId = sharePreference.getString("USER_ID",null).toString()
        data.addProperty("token", sharePreference.getString("TOKEN",null))
        accViewModel.getInfoAboutUser(userId,data)



        accViewModel.observerUserLiveData().observe(viewLifecycleOwner){user ->

            if (user.login!!.isNotEmpty() && user.email!!.isNotEmpty()){

                Handler(Looper.getMainLooper()).postDelayed({
                    binding.scrollView.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                },1)
            }

            binding.loginEt.hint = user.login
            binding.emailEt.hint = user.email
        }
    }
}