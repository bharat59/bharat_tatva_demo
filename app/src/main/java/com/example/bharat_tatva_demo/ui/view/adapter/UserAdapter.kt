package com.example.bharat_tatva_demo.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bharat_tatva_demo.R
import com.example.bharat_tatva_demo.data.response.UserData
import com.example.bharat_tatva_demo.databinding.ItemUserBinding

class UserAdapter(private var mList: ArrayList<UserData>) :
    RecyclerView.Adapter<UserAdapter.UserVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        return UserVH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        holder.bindData(mList[position])
    }

    override fun getItemCount(): Int = mList.size

    inner class UserVH(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(itemData: UserData) = binding.apply {
            mData = itemData
            executePendingBindings()
        }
    }
}