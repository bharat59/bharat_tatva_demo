package com.example.bharat_tatva_demo.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bharat_tatva_demo.R
import com.example.bharat_tatva_demo.data.GridModel
import com.example.bharat_tatva_demo.databinding.ItemGridBinding

class GridAdapter(private var mList: ArrayList<GridModel>, private var listener : GridClickListener) :
    RecyclerView.Adapter<GridAdapter.GridVH>() {

    interface GridClickListener {
        fun onGridClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridVH {
        return GridVH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_grid,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GridVH, position: Int) {
        holder.bindData(mList[position])
        holder.binding.txtGridPos.setOnClickListener {
            if(!mList[position].isClickedOnce){
                listener.onGridClick(position)
            }
        }
    }

    override fun getItemCount(): Int = mList.size

    inner class GridVH(val binding: ItemGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(itemData: GridModel) = binding.apply {
            mData = itemData
            executePendingBindings()
        }
    }
}