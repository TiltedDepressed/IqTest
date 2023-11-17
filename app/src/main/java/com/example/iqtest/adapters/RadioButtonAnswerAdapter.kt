package com.example.iqtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iqtest.databinding.RadiobuttonRecyclerItemBinding
import com.example.iqtest.model.Answer
import com.example.iqtest.model.User

class RadioButtonAnswerAdapter: RecyclerView.Adapter<RadioButtonAnswerAdapter.ViewHolder>() {

    private var answerList = ArrayList<Answer>()
    var onItemClick : ((Answer) -> Unit)? = null
    private var lastCheckedPosition = -1

    fun setAnswerList(answerList: List<Answer>){
        this.answerList = answerList as ArrayList<Answer>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RadiobuttonRecyclerItemBinding): RecyclerView.ViewHolder(binding.root){

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RadioButtonAnswerAdapter.ViewHolder {
        return ViewHolder(
            RadiobuttonRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: RadioButtonAnswerAdapter.ViewHolder, position: Int) {

        holder.binding.radioButton.text = answerList[position].answer

        if(position == lastCheckedPosition){
            holder.binding.radioButton.isChecked = true
        } else{
            holder.binding.radioButton.isChecked = false
        }


        holder.binding.radioButton.setOnClickListener {
            lastCheckedPosition = holder.adapterPosition
            notifyItemRangeChanged(0,answerList.size)
            onItemClick!!.invoke(answerList[position])
        }

    }

    override fun getItemCount(): Int {
        return answerList.size
    }
}