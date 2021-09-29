package com.huni.ch11_jetpack_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.huni.ch11_jetpack_library.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    val TAG : String = SecondActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_second)

        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Activity 코드로 up 버튼 정의
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //up 버튼 정의
    // 1. Manifest 에서
    // 2. 소스 코드에서 - supportActionBar?.setDisplayHomeAsUpEnabled(true)
    override fun onSupportNavigateUp(): Boolean {
        Log.e(TAG, "onSupportNavigateUp - SUCCESS!!!")
        //코드에서 사용하면 이거 호출해줘야한다.!!
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    
    // 메뉴 구성
    // onCreateOptionsMenu - Activity 실행시 호출
    // onPrepareOptionsMenu - Activity 실행시 호출 + 오버플로 메뉴가 나타날 때마다 반복 호출


}