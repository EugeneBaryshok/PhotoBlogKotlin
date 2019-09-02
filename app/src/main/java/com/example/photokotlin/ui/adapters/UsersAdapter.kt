package com.example.photokotlin.ui.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.photokotlin.R
import com.example.photokotlin.data.model.User
import com.example.photokotlin.ui.OnItemClickListener
import  kotlinx.android.synthetic.main.user_item.view.*

//kotlinx.android.synthetic.main.item_repository.view.*
import java.util.ArrayList

class UsersAdapter(val userList: List<User>, val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return MyViewHolder(view,itemClickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userModel = userList[position]
        holder.bindData(userModel)
    }

    override fun getItemCount() = userList.size

    class MyViewHolder(itemView: View, val itemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        fun bindData(userModel: User) {
            val userName = userModel.name

            itemView.tv_user_id.setText(userName)
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    v?.let { itemClickListener.onClick(it, adapterPosition) }
                }
            })
        }
    }


}
