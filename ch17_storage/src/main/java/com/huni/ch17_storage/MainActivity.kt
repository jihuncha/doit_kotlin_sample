package com.huni.ch17_storage

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.huni.ch17_storage.databinding.ActivityMainBinding
import com.huni.ch17_storage.ui.SettingActivity
import java.io.File
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            //퍼미션 다 받은지 체크하기 위함
            var count = 0

            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value

                if (isGranted) {
                    Log.d(TAG, "permissionName/$permissionName , isGranted - $isGranted")
                    count += 1
                } else {
                    Log.e(TAG, "error!!")
                }
            }

            if (count == 2) {
                //공용 저장소 접근...
                val projection = arrayOf(
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DISPLAY_NAME
                )

                //TODO 권한요청을 해줘야 확인 가능!!!
                val cursor = contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    null
                )

                cursor?.let {
                    while (cursor.moveToNext()) {
                        Log.d(TAG, "_id : ${cursor.getLong(0)}, name : ${cursor.getString(1)}")
                    }
                }

                //커서위치 초기화
//            cursor?.moveToFirst()

                if (cursor!!.moveToFirst()) {
                    //이미지 파일의 Uri 값 가져오기
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        //이미지 식별자
                        cursor!!.getLong(0)
                    )

                    Log.d(TAG, "contentUri - $contentUri")

                    //이미지 데이터 가져오기
                    val resolver = applicationContext.contentResolver
                    resolver.openInputStream(contentUri).use { stream ->
                        // stream 객체에서 작업 수행
                        val option = BitmapFactory.Options()
                        option.inSampleSize = 10
                        val bitmap = BitmapFactory.decodeStream(stream, null, option)
                        binding.ivImage.setImageBitmap(bitmap)
                    }
                }

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSaveText.setOnClickListener {
            val file = File(filesDir, "test.txt")
            val writeStream: OutputStreamWriter = file.writer()
            writeStream.write("hello world")
            writeStream.flush()

            Toast.makeText(baseContext, "save file!!", Toast.LENGTH_SHORT).show()
        }

        binding.btTest.setOnClickListener {
            val permission = arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            requestPermissionLauncher.launch(permission)
        }

        binding.btSetting.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingActivity::class.java)
            startActivity(intent)
        }

        //외부저장소 사용 여부
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            Log.d(TAG, "MOUNTED!!")
        } else {
            Log.d(TAG, "UNMOUNTED!!")
        }

        //외부저장소 - 앱별저장소 접근
        val file: File? = getExternalFilesDir(null)
        Log.d(TAG, "${file?.absolutePath}")

        //경로 - /storage/emulated/0/Android/data/com.huni.ch17_storage/files 
        //null 대신에 DIRECTORY_PICTURES, DOCUMENTS, MUSIC 등등 사용가능

        //공용 저장소 접근
//        val projection = arrayOf(
//            MediaStore.Images.Media._ID,
//            MediaStore.Images.Media.DISPLAY_NAME
//        )
//
//        //TODO 권한요청을 해줘야 확인 가능!!!
//        val cursor = contentResolver.query(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            projection,
//            null,
//            null,
//            null
//        )
//
//        Log.d(TAG, "check - $cursor")
//
//        cursor?.let {
//            Log.d(TAG, "test - $it")
//            while (cursor.moveToNext()) {
//                Log.d(TAG, "_id : ${cursor.getLong(0)}, name : ${cursor.getString(1)}")
//            }
//        }


        //SharedPreferences
        //해당 Activity class 명으로 자동 생성됨
        val sharedPref = getPreferences(Context.MODE_PRIVATE)

        //앱 전체 데이터를 저장할 경우
        val sharedPrefAppAll = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        //SharedPreferences.Editor 객체로 수정하여 commit()호출하면 수정된다.
        sharedPref.edit().run {
            putString("data1", "hello")
            putInt("data2", 10)
            commit()
        }

        //데이터 꺼내오기
        val data1 = sharedPref.getString("data1", "world")
        val data2 = sharedPref.getInt("data2", 20)

        Log.d(TAG, "datacheck - $data1 , $data2")


    }
}