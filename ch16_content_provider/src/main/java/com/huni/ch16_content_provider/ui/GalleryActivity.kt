package com.huni.ch16_content_provider.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huni.ch16_content_provider.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {
    companion object {
        val TAG : String = GalleryActivity::class.java.simpleName
    }

    private lateinit var binding : ActivityGalleryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}