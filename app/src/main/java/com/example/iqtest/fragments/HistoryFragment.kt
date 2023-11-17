package com.example.iqtest.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.iqtest.adapters.ResultListAdapter
import com.example.iqtest.databinding.FragmentHistoryBinding
import com.example.iqtest.viewModel.HistoryViewModel
import com.google.gson.JsonObject

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    private lateinit var historyViewModel: HistoryViewModel

    private lateinit var resultListAdapter: ResultListAdapter

    private lateinit var sharePreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]

        resultListAdapter = ResultListAdapter()

        sharePreference = this.requireActivity().getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)

        val id = sharePreference.getString("USER_ID",null)
        val token = sharePreference.getString("TOKEN",null)
        val data = JsonObject()
        data.addProperty("token",token)

        historyViewModel.getResultsByUserId(id.toString(),data)



        historyViewModel.observerResultListLiveData().observe(this){resultList->
            resultListAdapter.setResultList(resultList)
        }
    }

    private fun prepareRecyclerView() {
        binding.recyclerView.apply {
            adapter = resultListAdapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()


    }

}