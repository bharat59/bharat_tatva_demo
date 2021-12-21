package com.example.bharat_tatva_demo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bharat_tatva_demo.R
import com.example.bharat_tatva_demo.data.response.UserData
import com.example.bharat_tatva_demo.network.ApiService
import com.example.bharat_tatva_demo.utils.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticalOneViewModel @Inject constructor(
    private var apiService: ApiService,
    private var context: Application
) : AndroidViewModel(context) {

    var userDataList : MutableLiveData<ArrayList<UserData>> = MutableLiveData()
    var isLoading : MutableLiveData<Boolean> = MutableLiveData(false)
    var errorMessage : MutableLiveData<String> = MutableLiveData("")
    var pageNo = 1
    var totalPages = 0

    fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            try {
                if (isNetworkAvailable(context)) {
                    val response = apiService.getUser(pageNo)
                    if (response.isSuccessful && response.body() != null) {
                        isLoading.postValue(false)
                        userDataList.postValue(response.body()?.data)
                        response.body()?.total_pages?.let { totalPages = it }
                    } else {
                        errorMessage.postValue(response.message())
                    }
                } else {
                    isLoading.postValue(false)
                    errorMessage.postValue(context.getString(R.string.msg_internet_not_available))
                }
            } catch (e : Exception) {
                isLoading.postValue(false)
                errorMessage.postValue(e.message)
            }
        }
    }

    fun loadData() {
        if (pageNo < totalPages) {
            pageNo++
            getUserData()
        }
    }
}