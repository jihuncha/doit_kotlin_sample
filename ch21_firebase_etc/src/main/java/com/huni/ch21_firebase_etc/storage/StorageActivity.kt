package com.huni.ch21_firebase_etc.storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huni.ch21_firebase_etc.databinding.ActivityStorageBinding

class StorageActivity : AppCompatActivity() {
    lateinit var binding: ActivityStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}