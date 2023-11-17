package com.example.iqtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iqtest.databinding.AdminRecyclerItemBinding
import com.example.iqtest.databinding.AnswerCreateRecyclerItemBinding
import com.example.iqtest.databinding.AnswerDeleteRecyclerItemBinding
import com.example.iqtest.databinding.AnswerRecyclerItemBinding
import com.example.iqtest.databinding.QuestionRecyclerItemBinding
import com.example.iqtest.model.Answer
import com.example.iqtest.model.Question
import com.example.iqtest.model.User

class AnswerDeleteAdapter(): RecyclerView.Adapter<AnswerDeleteAdapter.ViewHolder>() {

    private var answerList = ArrayList<Answer>()
    var onItemClick : ((Answer) -> Unit)? = null

    fun setAnswerList(userList: List<Answer>){
        this.answerList = userList as ArrayList<Answer>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binging: AnswerDeleteRecyclerItemBinding): RecyclerView.ViewHolder(binging.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerDeleteAdapter.ViewHolder {
       return ViewHolder(
           AnswerDeleteRecyclerItemBinding.inflate(
               LayoutInflater.from(parent.context)
           )
       )
    }

    override fun onBindViewHolder(holder: AnswerDeleteAdapter.ViewHolder, position: Int) {

         holder.binging.answerText.text = answerList[position].answer

        holder.binging.deleteButton.setOnClickListener {
            onItemClick!!.invoke(answerList[position])
        }


    }

    override fun getItemCount(): Int {
       return answerList.size
    }
}