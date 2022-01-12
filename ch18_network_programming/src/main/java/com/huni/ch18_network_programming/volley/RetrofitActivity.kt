package com.huni.ch18_network_programming.volley

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huni.ch18_network_programming.databinding.ActivityRetrofitBinding
import com.huni.ch18_network_programming.volley.data.ApiRequestFactory
import com.huni.ch18_network_programming.volley.data.RetrofitInterface

class RetrofitActivity : AppCompatActivity() {
    companion object {
        val TAG = RetrofitActivity::class.java.simpleName
    }

    private lateinit var binding : ActivityRetrofitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetrofitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = ApiRequestFactory.retrofit

        //Call 객체 반환
        val temp = retrofit.doGetUserList("1")
    }
}