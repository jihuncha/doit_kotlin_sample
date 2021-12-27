package com.huni.ch16_content_provider.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.huni.ch16_content_provider.databinding.ActivityCameraBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
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

    private lateinit var binding: ActivityCameraBinding

    private var filePath: String? = null

    private val getImageCaptureResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
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
        binding.btShareFile.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding.dataSend.id -> {
                Log.d(TAG, "onClick/dataSend")
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                getImageCaptureResult.launch(intent)
            }

            binding.btShareFile.id -> {
                Log.d(TAG, "onClick/btShareFile")
                beforeStartShare()
            }
        }
    }

    private fun showImage(bitmap: Bitmap) {
        Log.d(TAG, "showImage")

        CoroutineScope(Dispatchers.Main).launch {
            binding.ivDataSendResult.setImageBitmap(bitmap)
        }
    }

    //////////////////////////////////////////////////////////
    //촬영한 사진 파일을 공유하는 방법
    //1. 앱에서 사진을 저장할 파일 생성
    //2. 사진 파일 정보를 포함한 인텐트를 전달해 카메라 앱을 실행
    //3. 카메라 앱으로 사진을 촬영하여 공유된 파일에 저장
    //4. 카메라 앱 종료 -> 성공/실패 반환
    //5. 카메라 앱이 저장된 사진 파일을 앱에서 이용
    //////////////////////////////////////////////////////////

    //외장 메모리에 파일 생성
    //1. getExternalStoragePublicDirectory() -> 모든 앱에서 사용할 수 있는 파일 (write시 권한 필요) 
    //2. getExternalFilesDir() -> 해당 앱에서만 이용할 수 있는 파일

    //api 24부터는 file:// 프로토콜로 구성된 URI를 외부에 노출 불가능 (strict mode)
    //content:// 프로토콜을 이용해야한다. -> FileProvider 클래스 사용
    //FileProvider - androidx 라이브러리르에서 제공하며 XML 설정을 기반으로 content:// 프로토콜로 구성된 URI 생성해줌

    //process 
    //1. res/xml 디렉터리에 파일 프로바이더용 xml 파일 생성 (file_path참고)
    //2. 상기 xml파일을 manifest에 등록
    //3. 카메라앱 연동

    val getImageShareResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "startShare RESULTOK!!")

            val option = BitmapFactory.Options()
            option.inSampleSize = 10 //단순히 10으로 지정
            val bitmap = BitmapFactory.decodeFile(filePath, option)
            bitmap?.let {
                binding.ivShareFile.setImageBitmap(bitmap)
            }
        }
    }

    private fun beforeStartShare() {
        Log.d(TAG, "beforeStartShare")

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            "JPEG_${timeStamp}_", ".jpg", storageDir
        )

        if (file != null) {
            filePath = file.absolutePath
        }

        startShare(file)
    }

    private fun startShare(file: File) {
        Log.d(TAG, "startShare")

        val photoURI: Uri = FileProvider.getUriForFile(
            this,
            "com.huni.ch16_content_provider.FileProvider", file
        )

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        getImageShareResult.launch(intent)

    }

}