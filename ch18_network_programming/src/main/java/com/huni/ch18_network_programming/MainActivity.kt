package com.huni.ch18_network_programming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huni.ch18_network_programming.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG : String = MainActivity::class.java.simpleName
    }

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}