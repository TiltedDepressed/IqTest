package com.example.iqtest.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.iqtest.R
import com.example.iqtest.adapters.RadioButtonAnswerAdapter
import com.example.iqtest.databinding.ActivityTestBinding
import com.example.iqtest.fragments.HomeFragment
import com.example.iqtest.viewModel.AdminViewModel
import com.example.iqtest.viewModel.TestViewModel
import com.google.gson.JsonObject

class TestActivity : AppCompatActivity() {
    private lateinit var bindig: ActivityTestBinding
    private lateinit var sharePreference: SharedPreferences
    private lateinit var testViewModel: TestViewModel
    private lateinit var adminViewModel: AdminViewModel
    private lateinit var radioButton : RadioButton
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButtonAdapter: RadioButtonAnswerAdapter
    private var counter = 0;
    private var totalPoints =0
    private var radioButtonsIdList = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig = ActivityTestBinding.inflate(layoutInflater)
        setContentView(bindig.root)

        sharePreference = this.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        testViewModel = ViewModelProvider(this)[TestViewModel::class.java]
        adminViewModel = ViewModelProvider(this)[AdminViewModel::class.java]
        radioButtonAdapter = RadioButtonAnswerAdapter()

        val token = sharePreference.getString("TOKEN",null)
        val count =  intent.getStringExtra("count").toString()
        val data = JsonObject()
        data.addProperty("token",token)

        testViewModel.generateTest(count,data)

        testViewModel.observerQuestionListLiveData().observe(this){questionList->

            bindig.questionText.text = questionList[counter].question
            bindig.counterText.text = (counter+1).toString() + "/" + "${questionList.size}"

            adminViewModel.getAnswersByQuestionId(questionList[counter].questionId.toString(),data)
            prepareRecyclerView()
            adminViewModel.observeAnswerListLiveData().observe(this){answerList ->
                radioButtonAdapter.setAnswerList(answerList)
            }
            bindig.nextButton.setOnClickListener {
                counter++
                adminViewModel.getAnswersByQuestionId(questionList[counter].questionId.toString(),data)
            //    adminViewModel.observeAnswerListLiveData().observe(this){answerList ->
            //    }
                bindig.questionText.text = questionList[counter].question
                bindig.counterText.text = (counter+1).toString() + "/" + "${questionList.size}"
            }
        }
        onAnswerRadioButtonClick()

        bindig.cancelButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun onAnswerRadioButtonClick(): Int {
        radioButtonAdapter.onItemClick = {answer ->
            totalPoints += answer.points!!.toInt()
        }
        return totalPoints
    }

    private fun prepareRecyclerView() {
        bindig.recyclerView.apply {
            adapter = radioButtonAdapter
        }
    }
}