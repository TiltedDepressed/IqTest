package com.example.iqtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iqtest.databinding.AdminRecyclerItemBinding
import com.example.iqtest.databinding.AnswerCreateRecyclerItemBinding
import com.example.iqtest.databinding.AnswerRecyclerItemBinding
import com.example.iqtest.databinding.QuestionRecyclerItemBinding
import com.example.iqtest.model.Answer
import com.example.iqtest.model.Question
import com.example.iqtest.model.User

class AnswersListAdapter(): RecyclerView.Adapter<AnswersListAdapter.ViewHolder>() {

    private var answerList = ArrayList<Answer>()
    var onItemClick : ((Answer) -> Unit)? = null

    fun setAnswerList(userList: List<Answer>){
        this.answerList = userList as ArrayList<Answer>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binging: AnswerCreateRecyclerItemBinding): RecyclerView.ViewHolder(binging.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswersListAdapter.ViewHolder {
       return ViewHolder(
           AnswerCreateRecyclerItemBinding.inflate(
               LayoutInflater.from(parent.context)
           )
       )
    }

    override fun onBindViewHolder(holder: AnswersListAdapter.ViewHolder, position: Int) {

         holder.binging.answerText.text = (position+1).toString()+ " " + answerList[position].answer


    }

    override fun getItemCount(): Int {
       return answerList.size
    }
}