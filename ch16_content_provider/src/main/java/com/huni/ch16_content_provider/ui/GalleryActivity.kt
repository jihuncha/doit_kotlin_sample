package com.huni.ch16_content_provider.ui

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.huni.ch16_content_provider.R
import com.huni.ch16_content_provider.databinding.ActivityGalleryBinding

/**
 * 갤러리 앱 연동
 * 안드로이드 이미지는 Drawable / Bitmap 으로 표현된다.
 * Bitmap 객체는 BitmapFactory에서 구현
 * BitmapFactory에서 이미지를 생성할 경우 OOM(Out Of Memory) 고려해야한다
 * Glide, Picasso, Coil 등 라이브러리 사용이 효율적일 수 있다
 * */
class GalleryActivity : AppCompatActivity() {
    companion object {
        val TAG: String = GalleryActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityGalleryBinding


    //권한요청용
//    val requestPermissionLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//                //갤러리 연동
//                val intent:Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                intent.type = "image/*"
//                getActivityResult.launch(intent)
//            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,  android.Manifest.permission.READ_CONTACTS)) {
//                //이전에 거부한 경우
//                Toast.makeText(baseContext, "Permission Necessary!!", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(baseContext, "Go to Setting!!", Toast.LENGTH_SHORT).show()
//            }
//        }

    //이미지 받아와서 처리하기
    //연락처 가져오는 ActivityResult
    private val getActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                try {

                    //inSampleSize 비율 계산 및 지정
                    val calRatio = calculateInSampleSize(activityResult.data!!.data!!,
                        200,
                        200)
//                        resources.getDimensionPixelSize(R.dimen.))
                    val options = BitmapFactory.Options()
                    options.inSampleSize = calRatio
                    //이미지 불러오기

                    var inputStream = contentResolver.openInputStream(activityResult.data!!.data!!)
                    val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                    inputStream!!.close()
                    inputStream = null
                    bitmap?.let { bitmapResult ->
                        binding.ivResult.setImageBitmap(bitmapResult)
                    } ?: let {
                        Log.e(TAG, "getActivityResult/Bitmap is NULL!!!!")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "getActivityResult/e - $e")
                    e.printStackTrace()
                }
            }

        }

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        Log.d(TAG, "calculateInSampleSize")

        val options = BitmapFactory.Options()
        //옵션만 설정
        options.inJustDecodeBounds = true

        try {
            var inputStream = contentResolver.openInputStream(fileUri)
            BitmapFactory.decodeStream(inputStream!!, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            Log.e(TAG, "calculateInSampleSize/e - $e")
            e.printStackTrace()
        }

        //TODO 이 구문은 뭐라고 설명해야할까??
        //bitmap의 크기를 반환
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while (halfHeight / inSampleSize >= reqHeight
                && halfWidth / inSampleSize >= reqWidth
            ) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //파일 가져오는 경우 -> oom 발생 가능성이 있따
        //inSampleSize 사용필요
//        val bitmap = BitmapFactory.decodeStream(inputStream)

        //inSampleSize sample
        val option = BitmapFactory.Options()
        option.inSampleSize = 4
        //option 적용 (4로 적용하면 2048x1356 -> 512x384) 로 줄여준다.
//        val bitmap = BitmapFactory.decodeStream(inputStream, null, option)

        binding.btStartGallery.setOnClickListener {
            //갤러리앱 연동
            val intent: Intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
//            startActivity(intent)

            getActivityResult.launch(intent)


//            val permission = android.Manifest.permission.CAMERA
//            requestPermissionLauncher.launch(permission)
        }
    }
}