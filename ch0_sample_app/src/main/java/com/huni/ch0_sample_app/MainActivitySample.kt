package com.huni.ch0_sample_app

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.huni.ch0_sample_app.databinding.ActivityMainBinding

class MainActivitySample : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

//    private lateinit var messenger: Messenger

    private lateinit var handler: Handler

    private lateinit var messenger: Messenger

    companion object {
        val TAG: String = MainActivitySample::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "sample - onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, MessengerTestSecondService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//            handler = CallbackHandler()
//
            messenger = Messenger(service)

            Log.d(TAG, "onServiceConnected")

            //메시지전송(내부)
//            val msg = Message()
//            msg.what = 10
//            msg.obj = "hello"
//            messenger.send(msg)

            //메시지전송(외부) - Parcelable이나 Bundle
//            val bundle = Bundle()
//            bundle.putString("data1", "hello")
//            bundle.putInt("data2", 10)
//
//            val msg = Message()
//            msg.what = 10
//            msg.obj = bundle
//            messenger.send(msg)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected")
        }

    }
}


class CallbackHandler: Handler(Looper.getMainLooper()) {
    override fun handleMessage(msg: Message) {
        Log.d("CallbackHandler" ,"handleMessage ${msg.toString()}")
        super.handleMessage(msg)
    }
}