package com.example.bharat_tatva_demo.data

import android.graphics.Color

data class GridModel(
    var position : Int = 0,
    var isClickable : Boolean = false,
    var isClickedOnce : Boolean = false,
    var bgColor : Int = Color.WHITE
) {
    fun setBgColor() : Int{
        return if (isClickable) {
            if (isClickedOnce) {
                Color.BLUE
            } else {
                Color.RED
            }
        } else {
            Color.WHITE
        }
    }
}
