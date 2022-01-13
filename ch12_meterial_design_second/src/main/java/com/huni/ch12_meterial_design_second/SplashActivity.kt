package com.huni.ch12_meterial_design_second

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.huni.ch12_meterial_design_second.databinding.ActivitySplashBinding
import com.huni.ch12_meterial_design_second.ui.MainActivity

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