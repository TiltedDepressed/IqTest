package com.example.iqtest.activities

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.iqtest.R
import com.example.iqtest.adapters.AdminAdapter
import com.example.iqtest.adapters.AdminDeleteAdapter
import com.example.iqtest.databinding.ActivityAdminBinding
import com.example.iqtest.databinding.AdminDeleteRecyclerItemBinding
import com.example.iqtest.databinding.ChangeAdminPageBinding
import com.example.iqtest.databinding.CreateAdminPageBinding
import com.example.iqtest.databinding.CreateQuestionPageBinding
import com.example.iqtest.databinding.DeleteAdminPageBinding
import com.example.iqtest.databinding.EditAdminPageBinding
import com.example.iqtest.datasource.ServiceBuilder
import com.example.iqtest.interfaces.Api
import com.example.iqtest.model.User
import com.example.iqtest.viewModel.AdminViewModel
import com.google.gson.JsonObject
import jp.wasabeef.blurry.Blurry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    private lateinit var sharePreference: SharedPreferences

    private lateinit var adminViewModel: AdminViewModel

    private lateinit var adminAdapter: AdminAdapter

    private lateinit var adminDeleteAdapter: AdminDeleteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)

        binding.createNewAdminButton.setOnClickListener {
            blurBackGround(true)
            createAdminShow()
        }
        binding.changeAdminDataButton.setOnClickListener {
            blurBackGround(true)
            changeAdminShow()
        }

        binding.deleteAdminButton.setOnClickListener {
            blurBackGround(true)
            deleteAdminShow()
        }

        binding.createNewQuestionButton.setOnClickListener {
            blurBackGround(true)
            createQuestionShow()
        }



    }

    private fun createQuestionShow() {
        val createQuestionPageBinding = CreateQuestionPageBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(createQuestionPageBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        createQuestionPageBinding.cancelButton.setOnClickListener {
            blurBackGround(false)
            dialog.dismiss()
        }
    }

    private fun deleteAdminShow() {
        val data = JsonObject()
        val token = sharePreference.getString("TOKEN",null).toString()
        data.addProperty("token",token)

        val deleteAdminBinding = DeleteAdminPageBinding.inflate(layoutInflater)
        val dialog = Dialog(this)

        dialog.setContentView(deleteAdminBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        prepareAdminDeleteRecyclerView(deleteAdminBinding)

        adminViewModel = ViewModelProvider(this)[AdminViewModel::class.java]

        adminViewModel.getUsersByRole("2", data)

        observerUserDeleteLiveData()

        onAdminDeleteButtonClick()

        deleteAdminBinding.cancelButton.setOnClickListener {
            blurBackGround(false)
            dialog.dismiss()
        }

    }

    private fun observerUserInfoLiveData(editAdminPageBinding: EditAdminPageBinding) {
        adminViewModel.observeUserLiveData().observe(this,){user->
              editAdminPageBinding.loginEt.hint = user.login
              editAdminPageBinding.passwordEt.hint = "Password"
              editAdminPageBinding.emailEt.hint = user.email
        }
    }

    private fun onAdminDeleteButtonClick() {
        val data = JsonObject()
        val token = sharePreference.getString("TOKEN",null).toString()
        data.addProperty("token",token)
        adminDeleteAdapter.onItemClick = {user->
            adminViewModel.deleteUserById(user.userId!!,data)
            adminViewModel.getUsersByRole("2", data)
        }

    }

    private fun observerUserDeleteLiveData() {
        adminViewModel.observerUserListLiveData().observe(this, Observer {userList ->
            adminDeleteAdapter.setUserList(userList)
            Log.d("zxc",userList.toString())
        })
    }

    private fun prepareAdminDeleteRecyclerView(binding: DeleteAdminPageBinding) {
        adminDeleteAdapter = AdminDeleteAdapter()
        binding.adminListRecyclerView.apply {
            adapter = adminDeleteAdapter
        }
    }

    private fun changeAdminShow() {

        val data = JsonObject()
        val token = sharePreference.getString("TOKEN",null).toString()
        data.addProperty("token",token)

        val changeAdminBinding = ChangeAdminPageBinding.inflate(layoutInflater)
        val dialog = Dialog(this)

        dialog.setContentView(changeAdminBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        prepareAdminRecyclerView(changeAdminBinding)

        adminViewModel = ViewModelProvider(this)[AdminViewModel::class.java]

        adminViewModel.getUsersByRole("2", data)

        observerUserLiveData()

        onAdminEditButtonClick(changeAdminBinding,dialog)

        changeAdminBinding.cancelButton.setOnClickListener {
            blurBackGround(false)
            dialog.dismiss()
        }



    }

    private fun onAdminEditButtonClick(changeAdminBinding: ChangeAdminPageBinding ,dialog: Dialog) {
        val data = JsonObject()
        val token = sharePreference.getString("TOKEN",null).toString()
        data.addProperty("token",token)
        adminAdapter.onItemClick = {user->

            adminViewModel.getInfoAboutUser(user.userId!!,data)
            val editAdminPageBinding = EditAdminPageBinding.inflate(layoutInflater)
            dialog.setContentView(editAdminPageBinding.root)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            observerUserInfoLiveData(editAdminPageBinding)

            editAdminPageBinding.changeAdminButton.setOnClickListener {

                val login = editAdminPageBinding.loginEt.text.toString()

                val password = editAdminPageBinding.passwordEt.text.toString()

                val email = editAdminPageBinding.emailEt.text.toString()

                if(editAdminPageBinding.loginEt.text.isNotEmpty()){
                    data.addProperty("login",login)
                    adminViewModel.changeUserData(user.userId!!,data)
                    editAdminPageBinding.loginEt.text.clear()
                    adminViewModel.getInfoAboutUser(user.userId!!,data)
                }
                if(editAdminPageBinding.passwordEt.text.isNotEmpty()){
                    data.addProperty("password",password)
                    adminViewModel.changeUserData(user.userId!!,data)
                    editAdminPageBinding.passwordEt.text.clear()
                    adminViewModel.getInfoAboutUser(user.userId!!,data)
                }
                if(editAdminPageBinding.emailEt.text.isNotEmpty()){
                    data.addProperty("email",email)
                    adminViewModel.changeUserData(user.userId!!,data)
                    editAdminPageBinding.emailEt.text.clear()
                    adminViewModel.getInfoAboutUser(user.userId!!,data)
                }


            }

            editAdminPageBinding.cancelButton.setOnClickListener {
                dialog.setContentView(changeAdminBinding.root)
            }
        }
    }

    private fun prepareAdminRecyclerView(binding:ChangeAdminPageBinding) {
        adminAdapter = AdminAdapter()
        binding.adminListRecyclerView.apply {
            adapter = adminAdapter
        }
    }

    private fun observerUserLiveData() {
        adminViewModel.observerUserListLiveData().observe(this, Observer {userList ->
            adminAdapter.setUserList(userList)
            Log.d("zxc",userList.toString())
        })
    }

    private fun createAdminShow() {
        val createAdminBinding = CreateAdminPageBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(createAdminBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        createAdminBinding.cancelButton.setOnClickListener {
            blurBackGround(false)
            dialog.dismiss()
        }

       createAdminBinding.createAdminButton.setOnClickListener {

           if(createAdminBinding.loginEt.text.isEmpty()){
               createAdminBinding.wrongLoginTextView.visibility = View.VISIBLE
               createAdminBinding.loginEt.background = resources.getDrawable(R.drawable.edit_text_background_red)
           } else{
               createAdminBinding.wrongLoginTextView.visibility = View.GONE
               createAdminBinding.loginEt.background = resources.getDrawable(R.drawable.edit_text_background)
           }
           if(createAdminBinding.emailEt.text.isEmpty()){
               createAdminBinding.wrongEmailTextView.visibility = View.VISIBLE
               createAdminBinding.emailEt.background = resources.getDrawable(R.drawable.edit_text_background_red)
           }else{
               createAdminBinding.wrongEmailTextView.visibility = View.GONE
               createAdminBinding.emailEt.background = resources.getDrawable(R.drawable.edit_text_background)
           }
           if(createAdminBinding.passwordEt.text.isEmpty()){
               createAdminBinding.wrongPasswordTextView.visibility = View.VISIBLE
               createAdminBinding.passwordEt.background = resources.getDrawable(R.drawable.edit_text_background_red)
           }else{
               createAdminBinding.wrongPasswordTextView.visibility = View.GONE
               createAdminBinding.passwordEt.background = resources.getDrawable(R.drawable.edit_text_background)
           }

           val login = createAdminBinding.loginEt.text.toString()

           val email = createAdminBinding.emailEt.text.toString()

           val password = createAdminBinding.passwordEt.text.toString()

           if(login.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
               createNewAdmin(login,email,password,dialog)
           }

           return@setOnClickListener
         }

       }

    private fun createNewAdmin(login: String, email: String, password: String, dialog: Dialog) {

        val data = JsonObject()

        data.addProperty("login",login)
        data.addProperty("email", email)
        data.addProperty("password", password)
        data.addProperty("role","2")

        val api = ServiceBuilder.buildService(Api::class.java)

        api.signUp(data).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    dialog.dismiss()
                    blurBackGround(false)
                    Toast.makeText(this@AdminActivity, "Admin created", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
               Log.e("AdminActivity",t.message.toString())
            }

        })


    }
    private fun blurBackGround(toggle: Boolean) {
        if (toggle) {
            Blurry.with(this).radius(20).sampling(10).onto(binding.cardView)
        } else {
            Blurry.delete(binding.cardView)
        }
    }
}

