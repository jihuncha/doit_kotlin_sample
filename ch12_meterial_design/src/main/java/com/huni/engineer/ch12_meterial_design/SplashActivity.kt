package com.huni.engineer.ch12_meterial_design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.huni.engineer.ch12_meterial_design.databinding.ActivityMainBinding
import com.huni.engineer.ch12_meterial_design.databinding.ActivitySplashBinding
import com.huni.engineer.ch12_meterial_design.ui.MainActivity

class SplashActivity : AppCompatActivity() {
    val TAG:String = SplashActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)

        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btNext.setOnClickListener {
            Log.d(TAG, "btNext - onClick!!")
            val intent : Intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}