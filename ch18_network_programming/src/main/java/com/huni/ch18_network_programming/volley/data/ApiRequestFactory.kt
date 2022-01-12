package com.huni.ch18_network_programming.volley.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRequestFactory {

    val retrofit: RetrofitInterface
        get() = Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitInterface::class.java)
}