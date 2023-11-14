package com.example.iqtest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iqtest.R
import com.example.iqtest.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // https://www.verywellmind.com/history-of-intelligence-testing-2795581

        binding.iQTestUsesTextView.text = "At the same time, others believe that IQ tests offer some value," +
                "particularly in certain situations. A few of the ways intelligence tests are" +
                "used today include:"

        binding.descriptionFirstTextView.text = "IQ tests are sometimes used in the " +
                "criminal justice system to help identify whether a defendant can " +
                "contribute to their own defense at trial, while others have used their " +
                "test results in an attempt to secure benefits in the form of Social " +
                "Security Disability."

        binding.descriptionSecondTextView.text = "Subtest scores on the WAIS-IV" +
                "can be useful in identifying learning disabilities. For instance, a" +
                "low score in some areas combined with a high score in others may " +
                "indicate that the person has a specific learning-related difficulty."
    }
}