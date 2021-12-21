package com.example.bharat_tatva_demo.data.response

data class UserDataResponse(
    val data: ArrayList<UserData> = ArrayList(),
    val page: Int? = null,
    val per_page: Int? = null,
    val support: Support? = null,
    val total: Int? = null,
    val total_pages: Int? = null
)