package com.huni.ch14_broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.huni.ch14_broadcast_receiver.BroadcastMakeTestActivity.Companion.TAG
import com.huni.ch14_broadcast_receiver.databinding.ActivityBroadcastMakeTestBinding

class BroadcastMakeTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBroadcastMakeTestBinding
    private lateinit var receiver: BroadcastReceiver

    companion object {
        val TAG : String = BroadcastMakeTestActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_broadcast_make_test)

        //동적 생성
//        receiver = object: BroadcastReceiver() {
//            override fun onReceive(context: Context?, intent: Intent?) {
//            }
//        }

        receiver = MyReceiver()

        //등록 - apply 사용
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON).apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_BATTERY_OKAY)
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_BATTERY_CHANGED)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(receiver, filter)

        //베터리상태가 변경되지 않더라도 현재 상태를 알기위해서는
        val intentFilterSecond = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = registerReceiver(null, intentFilterSecond)
        Log.d(TAG, "batteryStatus - ${batteryStatus.toString()}")

        //인텐트의 엑스트라를 이용하여 배터리 상태 파악
        val status = batteryStatus!!.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
            val chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
            when (chargePlug) {
                //저속충전
                BatteryManager.BATTERY_PLUGGED_USB -> Log.d(TAG, "usb charge")
                //고속충전
                BatteryManager.BATTERY_PLUGGED_AC -> Log.d(TAG, "ac charge")
            }
        } else {
            Log.e(TAG, "No Charging!!")
        }

        //베터리 충전량 %로 출력
        val level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        val batteryPct = level / scale.toFloat() * 100
        Log.d(TAG, "my Battery - ${batteryPct}")

        binding = ActivityBroadcastMakeTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)

        super.onDestroy()
    }
}

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            Intent.ACTION_SCREEN_ON ->
                Log.d(TAG, "ACTION_SCREEN_ON")
            Intent.ACTION_SCREEN_OFF ->
                Log.d(TAG, "ACTION_SCREEN_OFF")
            Intent.ACTION_BATTERY_OKAY ->
                Log.d(TAG, "ACTION_BATTERY_OKAY")
            Intent.ACTION_BATTERY_LOW ->
                Log.d(TAG, "ACTION_BATTERY_LOW")
            Intent.ACTION_BATTERY_CHANGED ->
                Log.d(TAG, "ACTION_BATTERY_CHANGED")
            Intent.ACTION_POWER_CONNECTED ->
                Log.d(TAG, "ACTION_POWER_CONNECTED")
            Intent.ACTION_POWER_DISCONNECTED ->
                Log.d(TAG, "ACTION_POWER_DISCONNECTED")
        }
    }

}