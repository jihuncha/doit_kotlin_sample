package com.huni.ch18_network_programming.library.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RetrofitInterface {
    @GET("api/users")
    fun doGetUserList(@Query("page") page:String): Call<TempDataClass>

    @GET
    fun getAvatarImage(@Url url:String): Call<ResponseBody>
}