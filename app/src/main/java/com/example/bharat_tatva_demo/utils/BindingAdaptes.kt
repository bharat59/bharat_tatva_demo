package com.example.bharat_tatva_demo.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.bharat_tatva_demo.R

@BindingAdapter("setProfilePic")
fun AppCompatImageView.setProfilePic(url: String?) {
    url?.let {
        Glide.with(this.context).load(it).circleCrop()
            .placeholder(R.drawable.ic_launcher_foreground).circleCrop().into(this)
    }
}