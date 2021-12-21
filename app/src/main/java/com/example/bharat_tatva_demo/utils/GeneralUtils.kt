package com.example.bharat_tatva_demo.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction


fun Activity.showToast(message : String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(message : String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun FragmentActivity.addFragment(@IdRes container : Int,fragment: Fragment) {
    val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
    transaction.apply {
        add(container,fragment,fragment.javaClass.simpleName)
        addToBackStack(fragment.tag)
        commit()
    }
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun isNetworkAvailable(context : Application): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}