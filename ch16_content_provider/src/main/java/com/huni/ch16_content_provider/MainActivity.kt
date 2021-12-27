package com.huni.ch16_content_provider

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.huni.ch16_content_provider.data.MyContentProvider
import com.huni.ch16_content_provider.databinding.ActivityMainBinding
import com.huni.ch16_content_provider.project.ProjectActivity
import com.huni.ch16_content_provider.ui.CameraActivity
import com.huni.ch16_content_provider.ui.ContactActivity
import com.huni.ch16_content_provider.ui.GalleryActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    private lateinit var binding:ActivityMainBinding
    private lateinit var contentProvider: MyContentProvider

    //권한요청용
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                //전화앱 연동
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:02-120"))
                startActivity(intent)
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,  android.Manifest.permission.READ_CONTACTS)) {
                //이전에 거부한 경우
                Toast.makeText(baseContext, "Permission Necessary!!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(baseContext, "Go to Setting!!", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btFirst.setOnClickListener(this)
        binding.btSecond.setOnClickListener(this)
        binding.btThird.setOnClickListener(this)
        binding.btFourth.setOnClickListener(this)
        binding.btFifth.setOnClickListener(this)
        binding.btProject.setOnClickListener(this)

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
            binding.btFourth.id -> {
                Log.d(TAG, "btFourth/onClick!!")

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.5662952, 126.9779451"))
                startActivity(intent)
            }
            binding.btFifth.id -> {
                Log.d(TAG, "btFifth/onClick!!")
                //permission 필요
                val permission = android.Manifest.permission.CALL_PHONE
                requestPermissionLauncher.launch(permission)
            }

            binding.btProject.id -> {
                Log.d(TAG, "btProject/onClick!!")

                val intent = Intent(this, ProjectActivity::class.java)
                startActivity(intent)
            }
        }
    }
}