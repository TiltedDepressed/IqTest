package com.example.iqtest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iqtest.R
import com.example.iqtest.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    private lateinit var bindig: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig = ActivityTestBinding.inflate(layoutInflater)
        setContentView(bindig.root)
    }
}