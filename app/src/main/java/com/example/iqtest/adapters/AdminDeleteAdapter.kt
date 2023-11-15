package com.example.iqtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iqtest.databinding.AdminDeleteRecyclerItemBinding
import com.example.iqtest.databinding.AdminRecyclerItemBinding
import com.example.iqtest.model.User

class AdminDeleteAdapter(): RecyclerView.Adapter<AdminDeleteAdapter.ViewHolder>() {

    private var userList = ArrayList<User>()
    var onItemClick : ((User) -> Unit)? = null

    fun setUserList(userList: List<User>){
        this.userList = userList as ArrayList<User>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binging: AdminDeleteRecyclerItemBinding): RecyclerView.ViewHolder(binging.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminDeleteAdapter.ViewHolder {
       return ViewHolder(
           AdminDeleteRecyclerItemBinding.inflate(
               LayoutInflater.from(parent.context)
           )
       )
    }

    override fun onBindViewHolder(holder: AdminDeleteAdapter.ViewHolder, position: Int) {
        holder.binging.adminName.text = userList[position].login

        holder.binging.deleteButton.setOnClickListener {
            onItemClick!!.invoke(userList[position])
        }

    }

    override fun getItemCount(): Int {
       return userList.size
    }
}