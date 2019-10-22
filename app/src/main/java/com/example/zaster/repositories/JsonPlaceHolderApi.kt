package com.example.zaster.repositories

import com.example.zaster.models.ApiResponse
import com.example.zaster.models.LoginInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface JsonPlaceHolderApi{

    @POST("./")
    fun createPost(@Body body: LoginInfo) : Call<ApiResponse>

}