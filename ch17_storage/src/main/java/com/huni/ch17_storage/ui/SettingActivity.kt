package com.huni.ch17_storage.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huni.ch17_storage.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    companion object {
        val TAG: String = SettingActivity::class.java.simpleName
    }

    private lateinit var binding : ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}