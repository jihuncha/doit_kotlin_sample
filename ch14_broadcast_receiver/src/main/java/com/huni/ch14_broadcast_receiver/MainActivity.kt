package com.huni.ch14_broadcast_receiver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.huni.ch14_broadcast_receiver.databinding.ActivityMainBinding
import com.huni.ch14_broadcast_receiver.project.MainProjectActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    companion object{
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btBrMakeTest.setOnClickListener{
            Log.d(TAG, "btBrMakeTest/onClick")
            val intent = Intent(this@MainActivity, BroadcastMakeTestActivity::class.java)
            startActivity(intent)
        }

        binding.btProject.setOnClickListener{
            Log.d(TAG, "btProject/onClick")
            val intent = Intent(this@MainActivity, MainProjectActivity::class.java)
            startActivity(intent)
        }
    }
}