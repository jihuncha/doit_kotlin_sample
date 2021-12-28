package com.huni.ch16_content_provider.project

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.huni.ch16_content_provider.R
import com.huni.ch16_content_provider.databinding.ActivityProjectBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ProjectActivity : AppCompatActivity() {
    companion object {
        val TAG: String = ProjectActivity::class.java.simpleName
        const val REQUEST_CODE: String = "REQUEST_CODE"
        //카메라/갤러리 분기를 위함
        const val GALLERY = 10
        const val CAMERA = 20
    }

    private lateinit var binding: ActivityProjectBinding
    lateinit var filePath: String

    //ActivityResult - Gallery
    private val getActivityResultGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            Log.d(TAG, "getActivityResultGallery - $activityResult")

            if (activityResult.resultCode == Activity.RESULT_OK) {
                val requestCode = activityResult.data?.extras?.getInt(REQUEST_CODE)

                Log.d(TAG ,"registerForActivityResult/requestCode - $requestCode")

                try {
                    //inSampleSize 비율 계산, 지정
                    val calRatio = calculateInSampleSize(
                        activityResult.data!!.data!!,
                        resources.getDimensionPixelSize(R.dimen.imgSize),
                        resources.getDimensionPixelSize(R.dimen.imgSize)
                    )
                    val option = BitmapFactory.Options()
                    option.inSampleSize = calRatio

                    //이미지 로딩
                    var inputStream = contentResolver.openInputStream(activityResult.data!!.data!!)
                    val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                    inputStream!!.close()
                    inputStream = null
                    bitmap?.let {
                        binding.userImageView.setImageBitmap(bitmap)
                    } ?: let {
                        Log.d(TAG, "bitmap null.............")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    //ActivityResult - Camera
     private val getActivityResultCamera =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                Log.d(TAG, "getActivityResultCamera - $activityResult")

                if (activityResult.resultCode == Activity.RESULT_OK) {
                    val requestCode = activityResult.data?.extras?.getInt(REQUEST_CODE)

                    Log.d(TAG ,"getActivityResultCamera/requestCode - $requestCode")

                    //camera app....................
                    val calRatio = calculateInSampleSize(
                        Uri.fromFile(File(filePath)),
                        resources.getDimensionPixelSize(R.dimen.imgSize),
                        resources.getDimensionPixelSize(R.dimen.imgSize)
                    )
                    val option = BitmapFactory.Options()
                    option.inSampleSize = calRatio
                    val bitmap = BitmapFactory.decodeFile(filePath, option)
                    bitmap?.let {
                        binding.userImageView.setImageBitmap(bitmap)
                    }
                }
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener {
            //gallery app........................
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            intent.putExtra(REQUEST_CODE, GALLERY)

            getActivityResultGallery.launch(intent)
//            startActivityForResult(intent, 10)
        }

        binding.cameraButton.setOnClickListener {
            //camera app......................
            //파일 준비...............
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile(
                "JPEG_${timeStamp}_", ".jpg", storageDir
            )

            if (file != null) {
                filePath = file.absolutePath
            }

            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.huni.ch16_content_provider.FileProvider", file
            )

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            intent.putExtra(REQUEST_CODE, CAMERA)
            getActivityResultCamera.launch(intent)
        }
    }

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver.openInputStream(fileUri)

            //inJustDecodeBounds 값을 true 로 설정한 상태에서 decodeXXX() 를 호출.
            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정 된다.
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //비율 계산........................
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

}