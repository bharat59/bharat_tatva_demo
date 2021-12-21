package com.example.bharat_tatva_demo.network

import com.example.bharat_tatva_demo.data.response.UserDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUser(@Query("page") pageNo : Int) : Response<UserDataResponse>
}