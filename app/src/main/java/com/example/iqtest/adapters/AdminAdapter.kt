package com.example.iqtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iqtest.databinding.AdminRecyclerItemBinding
import com.example.iqtest.model.User

class AdminAdapter(): RecyclerView.Adapter<AdminAdapter.ViewHolder>() {

    private var userList = ArrayList<User>()
    var onItemClick : ((User) -> Unit)? = null

    fun setUserList(userList: List<User>){
        this.userList = userList as ArrayList<User>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binging: AdminRecyclerItemBinding): RecyclerView.ViewHolder(binging.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminAdapter.ViewHolder {
       return ViewHolder(
           AdminRecyclerItemBinding.inflate(
               LayoutInflater.from(parent.context)
           )
       )
    }

    override fun onBindViewHolder(holder: AdminAdapter.ViewHolder, position: Int) {
        holder.binging.adminName.text = userList[position].login
    }

    override fun getItemCount(): Int {
       return userList.size
    }
}