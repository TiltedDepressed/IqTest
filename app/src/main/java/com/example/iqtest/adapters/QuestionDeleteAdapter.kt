package com.example.iqtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iqtest.databinding.AdminRecyclerItemBinding
import com.example.iqtest.databinding.QuestionDeleteRecyclerItemBinding
import com.example.iqtest.databinding.QuestionRecyclerItemBinding
import com.example.iqtest.model.Question
import com.example.iqtest.model.User

class QuestionDeleteAdapter(): RecyclerView.Adapter<QuestionDeleteAdapter.ViewHolder>() {

    private var questionList = ArrayList<Question>()
    var onItemClick : ((Question) -> Unit)? = null

    fun setQuestionList(userList: List<Question>){
        this.questionList = userList as ArrayList<Question>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binging: QuestionDeleteRecyclerItemBinding): RecyclerView.ViewHolder(binging.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionDeleteAdapter.ViewHolder {
       return ViewHolder(
           QuestionDeleteRecyclerItemBinding.inflate(
               LayoutInflater.from(parent.context)
           )
       )
    }

    override fun onBindViewHolder(holder: QuestionDeleteAdapter.ViewHolder, position: Int) {
        holder.binging.questionText.text = questionList[position].question

        holder.binging.deleteButton.setOnClickListener {
            onItemClick!!.invoke(questionList[position])
        }

    }

    override fun getItemCount(): Int {
       return questionList.size
    }
}