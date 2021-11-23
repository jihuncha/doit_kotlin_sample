package com.huni.ch15_service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import com.huni.ch15_service.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.btSample1.setOnClickListener(this)
        setContentView(binding.root)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding.btSample1.id -> {
//                Log.d(TAG, "test1")

                //startService
//                val intent = Intent(this, MyService::class.java)
//                startService(intent)

                //외부 앱의 경우 암시적 인텐트 사용필요 -> 패키지 name 전달
//                val intent = Intent("ACTION_OUTER_SERVICE")
//                intent.setPackage("com.example.test_outer")
//                startService(intent)

                //stopService를 통해 종료
//                val intent = Intent(this, MyService::class.java)
//                stopService(intent)


                //bindService
//                val connection: ServiceConnection = object : ServiceConnection {
//                    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
//                        //서비스 구동시 자동 호출
//                    }
//
//                    override fun onServiceDisconnected(p0: ComponentName?) {
//                        //서비스 소멸시 자동 호출
//                    }
//                }
//
//                val intent = Intent(this, MyService::class.java)
//                //intent, connection, flag
//                bindService(intent, connection, Context.BIND_AUTO_CREATE)
//
//                //종료
//                unbindService(connection)


            }
        }
    }
}