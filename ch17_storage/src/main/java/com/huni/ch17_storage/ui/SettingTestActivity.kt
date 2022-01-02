package com.huni.ch17_storage.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huni.ch17_storage.databinding.ActivityTestSettingBinding

class SettingTestActivity : AppCompatActivity() {
    companion object {
        val TAG: String = SettingTestActivity::class.java.simpleName
    }

    private lateinit var binding : ActivityTestSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}