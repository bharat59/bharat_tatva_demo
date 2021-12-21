package com.example.bharat_tatva_demo.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bharat_tatva_demo.data.GridModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.floor
import kotlin.math.sqrt

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    var number : MutableLiveData<String> = MutableLiveData(null)
    var isWinner : MutableLiveData<Boolean> = MutableLiveData(false)
    var mGridModelList : MutableLiveData<ArrayList<GridModel>> = MutableLiveData()
    private var gridList : ArrayList<GridModel> = ArrayList()

    // return true if number is perfect square root else false
    fun checkIfNumberIsSquaredRoot() : Boolean {
        val sqrt = number.value?.toDouble()?.let { sqrt(it) }
        val floor = sqrt?.let { floor(it) }
        return sqrt == floor
    }

    //populate data from given number
    fun getGridModelData(){
        gridList = ArrayList()
        for (i in 0 until number.value?.toInt()!!) {
            gridList.add(GridModel(i.plus(1)))
        }
        mGridModelList.value = gridList
    }

    //span count for given number
    fun getSpanCountForGivenNumber() : Int = sqrt(number.value.toString().toDouble()).toInt()

    private fun isWinner() : Boolean? = mGridModelList.value?.all { it.isClickedOnce }

    fun startGame() {
        viewModelScope.launch {
            //delay for 2 sec
            delay(2000)
            if (isWinner() == false) {
                gridList = ArrayList()
                mGridModelList.value?.let { gridList.addAll(it) }
                if (!mGridModelList.value.isNullOrEmpty()){
                    gridList.filter { !it.isClickedOnce }.random().apply {
                        isClickable = true
                    }
                    mGridModelList.value = gridList
                }
            } else {
                isWinner.value = true
            }
        }
    }

    fun saveCheckPoint(position: Int) {
        gridList = ArrayList()
        mGridModelList.value?.let { gridList.addAll(it) }
        gridList[position].apply {
            isClickedOnce = true
        }
        startGame()
        mGridModelList.value = gridList
    }
}