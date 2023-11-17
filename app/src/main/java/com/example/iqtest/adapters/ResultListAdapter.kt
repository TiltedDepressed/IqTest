package com.example.iqtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iqtest.databinding.AdminRecyclerItemBinding
import com.example.iqtest.databinding.AnswerCreateRecyclerItemBinding
import com.example.iqtest.databinding.AnswerRecyclerItemBinding
import com.example.iqtest.databinding.QuestionRecyclerItemBinding
import com.example.iqtest.databinding.ResultRecyclerItemBinding
import com.example.iqtest.model.Answer
import com.example.iqtest.model.Result
import com.example.iqtest.model.Question
import com.example.iqtest.model.User

class ResultListAdapter(): RecyclerView.Adapter<ResultListAdapter.ViewHolder>() {

    private var resultList = ArrayList<Result>()
    var onItemClick : ((Result) -> Unit)? = null

    fun setResultList(userList: List<Result>){
        this.resultList = userList as ArrayList<Result>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binging: ResultRecyclerItemBinding): RecyclerView.ViewHolder(binging.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultListAdapter.ViewHolder {
       return ViewHolder(
           ResultRecyclerItemBinding.inflate(
               LayoutInflater.from(parent.context)
           )
       )
    }

    override fun onBindViewHolder(holder: ResultListAdapter.ViewHolder, position: Int) {
       holder.binging.totalPointsTextView.text = resultList[position].points
    }

    override fun getItemCount(): Int {
       return resultList.size
    }
}