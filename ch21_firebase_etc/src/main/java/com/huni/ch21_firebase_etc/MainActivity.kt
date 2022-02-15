package com.huni.ch21_firebase_etc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huni.ch21_firebase_etc.databinding.ActivityMainBinding
import com.huni.ch21_firebase_etc.storage.StorageActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btFirebaseStorage.setOnClickListener {
            val intent = Intent(this, StorageActivity::class.java)
            startActivity(intent)
        }
    }
}