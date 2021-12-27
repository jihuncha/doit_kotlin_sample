package com.huni.ch16_content_provider

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.huni.ch16_content_provider.data.MyContentProvider
import com.huni.ch16_content_provider.databinding.ActivityMainBinding
import com.huni.ch16_content_provider.ui.CameraActivity
import com.huni.ch16_content_provider.ui.ContactActivity
import com.huni.ch16_content_provider.ui.GalleryActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    private lateinit var binding:ActivityMainBinding
    private lateinit var contentProvider: MyContentProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btFirst.setOnClickListener(this)
        binding.btSecond.setOnClickListener(this)
        binding.btThird.setOnClickListener(this)

        //시스템의 콘텐츠 프로바이더 사용
//        contentProvider = MyContentProvider()
//        contentProvider.query(Uri.parse("content://com.huni.ch16_content_provider"), null, null, null, null)

    }

    override fun onClick(view: View?) {
        when(view?.id) {
            binding.btFirst.id -> {
                Log.d(TAG, "btFirst/onClick!!")

                val intent = Intent(this, ContactActivity::class.java)
                startActivity(intent)
            }

            binding.btSecond.id -> {
                Log.d(TAG, "btSecond/onClick!!")

                val intent = Intent(this, GalleryActivity::class.java)
                startActivity(intent)
            }
            binding.btThird.id -> {
                Log.d(TAG, "btThird/onClick!!")

                val intent = Intent(this, CameraActivity::class.java)
                startActivity(intent)
            }
        }
    }
}