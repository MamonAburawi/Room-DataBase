package com.example.roomapp.fragments.list

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.User
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class ListAdapter(): RecyclerView.Adapter<ListAdapter.MyViewHolder>(){


    private var userList = emptyList<User>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItemData = userList[position]

        holder.id.text= currentItemData.id.toString()
        holder.firstName.text = currentItemData.firstName
        holder.lastName.text = currentItemData.lastName
        holder.age.text = currentItemData.age.toString()


        holder.itemView.rowLayout.setOnClickListener {  // this code when click on single item of recycler View ...
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItemData)
            holder.itemView.findNavController().navigate(action)
        }
    }


    override fun getItemCount(): Int {
        return userList.size
    }



    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var id = itemView.id_txt as TextView
        var firstName = itemView.firstName_txt as TextView
        var lastName = itemView.lastName_txt as TextView
        var age = itemView.age_txt as TextView
    }



    // this function for set the data to recycler View..
    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged() // this code for refresh the recyclerView..
    }



}