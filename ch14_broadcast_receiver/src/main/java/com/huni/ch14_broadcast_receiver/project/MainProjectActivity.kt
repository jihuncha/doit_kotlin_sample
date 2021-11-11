package com.huni.ch14_broadcast_receiver.project

import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huni.ch14_broadcast_receiver.R
import com.huni.ch14_broadcast_receiver.databinding.ActivityMainProjectBinding

class MainProjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainProjectBinding
    private var batteryPct = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //add........................
        registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))!!.apply {
            when (getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
                BatteryManager.BATTERY_STATUS_CHARGING -> {
                    when (getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)) {
                        BatteryManager.BATTERY_PLUGGED_USB -> {
                            binding.chargingResultView.text = "USB Plugged"
                            binding.chargingImageView.setImageBitmap(BitmapFactory.decodeResource(
                                resources,
                                R.drawable.usb))
                        }
                        BatteryManager.BATTERY_PLUGGED_AC -> {
                            binding.chargingResultView.text = "AC Plugged"
                            binding.chargingImageView.setImageBitmap(BitmapFactory.decodeResource(
                                resources,
                                R.drawable.ac))
                        }
                    }
                }
                else -> {
                    binding.chargingResultView.text = "Not Plugged"
                }
            }

            //잔량 표기 (%로)
            val level = getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            batteryPct = level / scale.toFloat() * 100
            binding.percentResultView.text = "$batteryPct %"
        }

        binding.button.setOnClickListener {
            val intent= Intent(this, MyProjectReceiver::class.java)
            intent.putExtra("battery", batteryPct)
            sendBroadcast(intent)
        }
    }
}