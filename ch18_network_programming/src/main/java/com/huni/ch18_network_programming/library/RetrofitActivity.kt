package com.huni.ch18_network_programming.library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.huni.ch18_network_programming.databinding.ActivityRetrofitBinding
import com.huni.ch18_network_programming.library.data.ApiRequestFactory
import com.huni.ch18_network_programming.library.data.TempDataClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

        //Call 객체
        val temp = retrofit.doGetUserList("1")

        //실제 통신 시작 (enqueue)
        temp.enqueue(object : Callback<TempDataClass> {
            override fun onResponse(call: Call<TempDataClass>, response: Response<TempDataClass>) {
                Log.d(TAG, "check - ${response.isSuccessful}")
                val tempList = response.body()
            }

            override fun onFailure(call: Call<TempDataClass>, t: Throwable) {
                Log.e(TAG, "onFailure - ${t.toString()}")
                call.cancel()
            }
        })
    }
}