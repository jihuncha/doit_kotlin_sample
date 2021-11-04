package com.huni.ch13_activity_component

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.huni.ch13_activity_component.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvGoIntentFilter.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                //action 을 manifest에 지정함
//                val intent = Intent("ACTION_EDIT", Uri.parse("http://www.google.com"))

                //좌표값 누르면 선택토록 나온다 -> 암시적 intent
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.7749, 127.4194"))
                //강제로 설정 가능 -> 구글맵으로
//                intent.setPackage("com.google.android.apps.maps")
                startActivity(intent)
            }
        })

        binding.tvGoBundleTest.setOnClickListener {
            val intent = Intent(MainActivity@this, BundleTestActivity::class.java)
            startActivity(intent)
        }

        binding.btCoroutine.setOnClickListener{
            val intent = Intent(MainActivity@this, CoroutineTestActivity::class.java)
            startActivity(intent)
        }
    }
}