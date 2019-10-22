package com.example.zaster.repositories

import com.example.zaster.models.*
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import retrofit2.Retrofit


@Singleton
class PostLoginInfo @Inject constructor(private val retrofit: Retrofit)
{
    lateinit var responseMessage: ResponseMessage
    fun postLoginDetails(loginInfo: LoginInfo) : ResponseMessage
    {
        retrofit.create(JsonPlaceHolderApi::class.java).createPost(loginInfo)
                .enqueue(object : Callback<ApiResponse> {
                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        responseMessage = ResponseMessage("Retrofit call failed", 0)
                    }

                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                         responseMessage = ResponseMessage(response.body().toString(), response.raw().code)
                    }
                })
        return responseMessage

    }
}