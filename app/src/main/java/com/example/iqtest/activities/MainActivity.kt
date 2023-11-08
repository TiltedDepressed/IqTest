package com.example.iqtest.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iqtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharePreference: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logOut.setOnClickListener {
            sharePreference = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharePreference.edit()
            editor.clear()
            editor.apply()
            val intent = Intent(this@MainActivity,AuthActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}