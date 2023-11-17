package com.example.iqtest.fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.iqtest.activities.AboutActivity
import com.example.iqtest.activities.TestActivity
import com.example.iqtest.databinding.FragmentHomeBinding
import com.example.iqtest.databinding.TestLengthDialogBinding
import com.example.iqtest.viewModel.AccountViewModel
import com.google.gson.JsonObject
import jp.wasabeef.blurry.Blurry

class HomeFragment : Fragment() {

    private  lateinit var binding: FragmentHomeBinding

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
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUserLiveData()

        binding.aboutButton.setOnClickListener {
            val intent = Intent(this.requireActivity(), AboutActivity::class.java)
            startActivity(intent)
        }

        binding.startTestButton.setOnClickListener {

            blurBackGround(true)
            showChoiceTestLengthDialog()
        }

    }

    private fun showChoiceTestLengthDialog() {
        val testLengthDialogBinding = TestLengthDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.setContentView(testLengthDialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        testLengthDialogBinding.shortTestButton.setOnClickListener {
            val intent = Intent(requireActivity(), TestActivity::class.java)
            intent.putExtra("count","5")
            startActivity(intent)
        }
        testLengthDialogBinding.mediumTestButton.setOnClickListener {
            val intent = Intent(requireActivity(), TestActivity::class.java)
            intent.putExtra("count","10")
            startActivity(intent)
        }
        testLengthDialogBinding.longTestButton.setOnClickListener {
            val intent = Intent(requireActivity(), TestActivity::class.java)
            intent.putExtra("count","15")
            startActivity(intent)
        }

        testLengthDialogBinding.cancelButton.setOnClickListener {
            blurBackGround(false)
            dialog.dismiss()
        }
    }

    private fun observeUserLiveData() {
        val data = JsonObject()
        val userId = sharePreference.getString("USER_ID",null).toString()
        data.addProperty("token", sharePreference.getString("TOKEN",null))
        accViewModel.getInfoAboutUser(userId,data)

        accViewModel.observerUserLiveData().observe(viewLifecycleOwner){user ->


            binding.userNameTextView.text = user.login
        }

    }

    private fun blurBackGround(toggle: Boolean) {
        if (toggle) {
            Blurry.with(requireContext()).radius(20).sampling(50).onto(binding.cardView)
        } else {
            Blurry.delete(binding.cardView)
        }
    }

}