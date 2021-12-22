package com.huni.ch15_service_project

import android.annotation.TargetApi
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.huni.ch15_outer.MyAIDLInterface
import com.huni.ch15_service_project.databinding.ActivityMainBinding
import com.huni.ch15_service_project.service.MyJobService
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding

    //Messenger
    lateinit var messenger: Messenger
    lateinit var replyMessenger: Messenger
    var messengerJob: Job? = null

    //aidl...........
    var aidlService: MyAIDLInterface? = null
    var aidlJob: Job? = null

    private var connectionMode: String = "none"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //messenger..........
        onCreateMessengerService()

        //aidl................
        onCreateAIDLService()

        //jobscheduler......................
        onCreateJobScheduler()
    }

    //destroy 에서 호출하면..? 백그라운드에서 돌아가고있을듯?
    override fun onStop() {
        Log.d(TAG, "onStop")

        super.onStop()
        if (connectionMode === "messenger") {
            onStopMessengerService()
        } else if (connectionMode === "aidl") {
            onStopAIDLService()
        }
        connectionMode = "none"
        changeViewEnable()
    }

    inner class HandlerReplyMessage : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            Log.d(TAG, "HandlerReplyMessage/handleMessage - $msg")
            super.handleMessage(msg)
            when (msg.what) {
                10 -> {
                    // 재생 후 지속 시간이 전송되면
                    val bundle = msg.obj as Bundle
                    bundle.getInt("duration")?.let {
                        when {
                            it > 0 -> {
                                //inner class 로 안하면 여기서 접근이 안됨!!
                                binding.messengerProgress.max = it
                                val backgroungScope = CoroutineScope(Dispatchers.Default + Job())
                                messengerJob = backgroungScope.launch {
                                    while (binding.messengerProgress.progress < binding.messengerProgress.max) {
                                        delay(1000)
                                        binding.messengerProgress.incrementProgressBy(1000)
                                    }
                                }

                                changeViewEnable()
                            }
                            else -> {
                                connectionMode = "none"
                                unbindService(messengerConnection)
                                changeViewEnable()
                            }
                        }
                    }
                }
            }
        }
    }

    fun changeViewEnable() {
        Log.d(TAG, "changeViewEnable - $connectionMode")

        when (connectionMode) {
            "messenger" -> {
                binding.messengerPlay.isEnabled = false
                binding.aidlPlay.isEnabled = false
                binding.messengerStop.isEnabled = true
                binding.aidlStop.isEnabled = false
            }
            "aidl" -> {
                binding.messengerPlay.isEnabled = false
                binding.aidlPlay.isEnabled = false
                binding.messengerStop.isEnabled = false
                binding.aidlStop.isEnabled = true
            }
            else -> {
                //초기상태. stop 상태. 두 play 버튼 활성상태
                binding.messengerPlay.isEnabled = true
                binding.aidlPlay.isEnabled = true
                binding.messengerStop.isEnabled = false
                binding.aidlStop.isEnabled = false

                binding.messengerProgress.progress = 0
                binding.aidlProgress.progress = 0
            }
        }
    }

    //messenger connection....................
    val messengerConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "messengerConnection/onServiceConnected")

            messenger = Messenger(service)

            val msg = Message()
            msg.replyTo = replyMessenger
            msg.what = 10
            messenger.send(msg)

            connectionMode = "messenger"
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d(TAG, "messengerConnection/onServiceDisconnected")
        }
    }

    private fun onCreateMessengerService() {
        Log.d(TAG, "onCreateMessengerService")
        replyMessenger = Messenger(HandlerReplyMessage())

        binding.messengerPlay.setOnClickListener {
            Log.d(TAG, "messengerPlay/onClick!!")

            val intent = Intent("ACTION_SERVICE_Messenger")
            intent.setPackage("com.huni.ch15_outer")
            bindService(intent, messengerConnection, Context.BIND_AUTO_CREATE)
        }

        binding.messengerStop.setOnClickListener {
            Log.d(TAG, "messengerStop/onClick!!")

            val msg = Message()
            msg.what = 20
            messenger.send(msg)

            unbindService(messengerConnection)
            messengerJob?.cancel()

            connectionMode = "none"
            changeViewEnable()
        }
    }

    private fun onStopMessengerService() {
        Log.d(TAG, "onStopMessengerService")

        val msg = Message()
        msg.what = 20
        messenger.send(msg)
        unbindService(messengerConnection)
    }

    //aidl connection .......................
    val aidlConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d(TAG, "aidlConnection/onServiceConnected")

            aidlService = MyAIDLInterface.Stub.asInterface(service)
            aidlService!!.start()
            binding.aidlProgress.max = aidlService!!.maxDuration
            val backgroundScope = CoroutineScope(Dispatchers.Default + Job())
            aidlJob = backgroundScope.launch {
                while (binding.aidlProgress.progress < binding.aidlProgress.max) {
                    delay(1000)
                    binding.aidlProgress.incrementProgressBy(1000)
                }
            }
            connectionMode = "aidl"
            changeViewEnable()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d(TAG, "aidlConnection/onServiceDisconnected")
            aidlService = null
        }
    }

    private fun onCreateAIDLService() {
        Log.d(TAG, "onCreateAIDLService")

        binding.aidlPlay.setOnClickListener {
            val intent = Intent("ACTION_SERVICE_AIDL")
            intent.setPackage("com.huni.ch15_outer")
            bindService(intent, aidlConnection, Context.BIND_AUTO_CREATE)
        }
        binding.aidlStop.setOnClickListener {
            aidlService!!.stop()
            unbindService(aidlConnection)
            aidlJob?.cancel()
            connectionMode = "none"
            changeViewEnable()
        }
    }

    private fun onStopAIDLService() {
        Log.d(TAG, "onStopAIDLService")

        unbindService(aidlConnection)
    }

    //JobScheduler
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onCreateJobScheduler(){
        Log.d(TAG, "onCreateJobScheduler")

        var jobScheduler: JobScheduler? = getSystemService<JobScheduler>()

        val builder = JobInfo.Builder(1, ComponentName(this, MyJobService::class.java))
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)

        val jobInfo = builder.build()

        jobScheduler!!.schedule(jobInfo)
    }
}