package com.huni.ch15_service.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huni.ch15_service.databinding.ActivityMessengerBinding

class MessengerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessengerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessengerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btMessengerTest.setOnClickListener {
            
        }
    }
}