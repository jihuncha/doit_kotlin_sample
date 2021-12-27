package com.huni.ch16_content_provider.project

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.huni.ch16_content_provider.databinding.ActivityProjectBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ProjectActivity : AppCompatActivity() {
    companion object {
        val TAG: String = ProjectActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityProjectBinding
    lateinit var filePath: String

    //연락처 가져오는 ActivityResult
    private val getActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            Log.d(TAG, "activityResult - ${activityResult.toString()}")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener {
            //gallery app........................
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"

            getActivityResult.launch(intent)
//            startActivityForResult(intent, 10)
        }
        binding.cameraButton.setOnClickListener {
            //camera app......................
            //파일 준비...............
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
            )
            filePath = file.absolutePath

            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.huni.ch16_content_provider.FileProvider", file
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//            startActivityForResult(intent, 20)

            getActivityResult.launch(intent)
        }
    }


}