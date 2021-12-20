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
import com.huni.ch15_service.sample.messenger.MessengerActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var serviceConnection: ServiceConnection

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.btSample1.setOnClickListener(this)
        binding.btSample2.setOnClickListener(this)
        setContentView(binding.root)

        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                //서비스 구동시 자동 호출
                //MyService 에서 반환받아 함수 사용 가능
                val binder = p1 as MyBinder
                Log.d(TAG, "onServiceConnected - ${binder.funB(5)}")
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                //서비스 소멸시 자동 호출
            }
        }

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
//                        //MyService 에서 반환받아 함수 사용 가능
//                        val binder = p1 as MyBinder
//                        Log.d(TAG, "test - ${binder.funB(5)}")
//                    }
//
//                    override fun onServiceDisconnected(p0: ComponentName?) {
//                        //서비스 소멸시 자동 호출
//                    }
//                }

                val intent = Intent(this, MyService::class.java)
                //intent, connection, flag
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

                //종료
//                unbindService(connection)
            }

            binding.btSample2.id -> {
                val intent:Intent = Intent(this@MainActivity, MessengerActivity::class.java)
                startActivity(intent)
            }


        }
    }

    override fun onStop() {
        super.onStop()

        //btn 1으로 실행한경우
        try {
            unbindService(serviceConnection)
        } catch (e:Exception) {
            Log.e(TAG, "e - $e")
        }

    }
}