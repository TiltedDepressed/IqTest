package com.example.iqtest.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.iqtest.R
import com.example.iqtest.databinding.ActivityTestEndBinding
import com.example.iqtest.viewModel.TestViewModel

class TestEndActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestEndBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestEndBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val points = intent.getIntExtra("points",1)

        binding.totalPointsTextView.text = points.toString()

        binding.cancelButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}