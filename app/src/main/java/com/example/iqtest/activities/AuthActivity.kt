package com.example.iqtest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iqtest.R
import com.example.iqtest.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}