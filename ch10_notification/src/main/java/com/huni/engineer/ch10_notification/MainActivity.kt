package com.huni.engineer.ch10_notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.huni.engineer.ch10_notification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSample.setOnClickListener {
            val intent: Intent = Intent(this, DialogTestActivity::class.java)

            startActivity(intent)
        }

        binding.btProject.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                //TODO this 만으로 안되는 이규..? this.MainActivity 느낌인가?
                val intent_two = Intent(this@MainActivity, KakaoAlarmSampleActivity::class.java)

                startActivity(intent_two)
            }
        })
    }
}