package com.huni.ch16_content_provider.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.huni.ch16_content_provider.databinding.ActivityCameraBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * 카메라앱 연동
 * 카메라 앱을 연동하여 사진을 촬영하고 그 결과를 돌려받는 방법은 2가지
 * 1. 사진 데이터를 가져오는 방법 - 파일로 저장하지않고 데이터만 넘겨줌 (구현이 쉬우나, 사진 데이터크기가 작다)
 * 2. 사진 파일을 공유하는 방법 - 파일로 저장하여 성공/실패 여부를 돌려준다.
 * */

class CameraActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        val TAG = CameraActivity::class.java.simpleName
    }

    private lateinit var binding : ActivityCameraBinding

    private val getImageCaptureResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "getImageCaptureResult RESULTOK!!")

            //bitmap가져오기
            val bitmap = activityResult.data?.extras?.get("data") as Bitmap
            showImage(bitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dataSend.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            binding.dataSend.id -> {
                Log.d(TAG, "onClick/dataSend")
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                getImageCaptureResult.launch(intent)
            }
        }
    }

    private fun showImage(bitmap: Bitmap) {
        Log.d(TAG, "showImage")

        CoroutineScope(Dispatchers.Main).launch {
            binding.ivDataSendResult.setImageBitmap(bitmap)
        }
    }
}