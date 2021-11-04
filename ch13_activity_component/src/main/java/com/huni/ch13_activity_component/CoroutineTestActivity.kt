package com.huni.ch13_activity_component

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.huni.ch13_activity_component.databinding.ActivityCoroutineTestBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

//ANR and Coroutine
class CoroutineTestActivity : AppCompatActivity() {
    val TAG:String = CoroutineTestActivity::class.java.simpleName

    private lateinit var binding: ActivityCoroutineTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutineTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btMainThread.setOnClickListener {
            checkInit()
        }

        binding.btThreadHandler.setOnClickListener {
            threadHandler()
        }

        binding.btCoroutine.setOnClickListener{
            coroutine()
        }
    }

    //해당 작업 진행중에 화면이 멈춰 있는 것을 볼 수 있음(ANR)
    private fun checkInit() {
        binding.tvTimeShow.text = ""
        var sum = 0L
        //20억
        var time = measureTimeMillis {
            for (i in 1..2_000_000_000) {
                sum += i
            }
        }

        Log.d(TAG, "checkInit - $time")
        binding.tvTimeShow.text = "result - $time"
    }

    //ThreadHandler구조 - API30에서 Deprecated
    private fun threadHandler() {
        Log.d(TAG, "threadHandler")
        binding.tvTimeShow.text = ""
        val handler = object:Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                binding.tvTimeShow.text = "sum: ${msg.arg1}"
            }
        }

        thread {
            var sum = 0L
            //20억
            var time = measureTimeMillis {
                for (i in 1..2_000_000_000) {
                    sum += i
                }
            }

            val message = Message()
            message.arg1 = time.toInt()
            handler.sendMessage(message)
            Log.d(TAG, "timeCheck - $time")
        }
    }

    //Coroutine
    private fun coroutine() {
        Log.d(TAG, "coroutine")
        binding.tvTimeShow.text = ""
        //내가 임시로 짠 코드
//        CoroutineScope(Dispatchers.Default).launch {
//            var sum = 0L
//            var time = measureTimeMillis {
//                for (i in 1..2_000_000_000) {
//                    sum += i
//                }
//            }
//            CoroutineScope(Dispatchers.Main).launch {
//                binding.tvTimeShow.text = "result - $time"
//            }
//        }

        val channel = Channel<Int>()
        val backgroundScope = CoroutineScope(Dispatchers.Default + Job())
        backgroundScope.launch {
            var sum = 0L
            var time = measureTimeMillis {
                for (i in 1..2_000_000_000) {
                    sum += i
                }
            }
            Log.d(TAG, "time - $time")
            channel.send(time.toInt())
        }

        val mainScope = GlobalScope.launch(Dispatchers.Main) {
            channel.consumeEach {
                binding.tvTimeShow.text = "sum : $it"
            }
        }
    }
    
}