package com.example.iqtest.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.iqtest.activities.AuthActivity
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