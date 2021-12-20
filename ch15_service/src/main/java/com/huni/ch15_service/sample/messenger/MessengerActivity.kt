package com.huni.ch15_service.sample.messenger

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import com.huni.ch15_service.databinding.ActivityMessengerBinding

class MessengerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessengerBinding
    private lateinit var messenger: Messenger

    companion object {
        val TAG : String = MessengerActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessengerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //내부앱
        binding.btMessengerTest.setOnClickListener {
            val intent = Intent(this, MessengerTestService::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }

        //외부앱
        binding.btMessengerTestOuter.setOnClickListener {
            Log.d(TAG, "btMessengerTestOuter onClick!!")
            val intentOuter:Intent = Intent("ACTION_OUTER_SERVICE")
            intentOuter.setPackage("com.huni.ch0_sample_app")
            bindService(intentOuter, connection, Context.BIND_AUTO_CREATE)
        }
    }

    val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected")
            messenger = Messenger(service)
            
            //메시지전송(내부)
//            val msg = Message()
//            msg.what = 10
//            msg.obj = "hello"
//            messenger.send(msg)

            //메시지전송(외부) - Parcelable이나 Bundle
            val bundle = Bundle()
            bundle.putString("data1", "hello")
            bundle.putInt("data2", 10)

            val msg = Message()
            msg.what = 10
            msg.obj = bundle
            messenger.send(msg)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            TODO("Not yet implemented")
        }

    }

    override fun onStop() {
        super.onStop()

        try {
            unbindService(connection)
        } catch (e:Exception) {
            Log.e(TAG, "e - $e")
        }

    }
}