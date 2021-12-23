package com.huni.ch16_content_provider.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.huni.ch16_content_provider.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {
    companion object {
        val TAG: String = ContactActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityContactBinding

    //연락처 가져오는 ActivityResult
    private val getActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
//            val result: String? = activityResult.data?.dataString
                //url 형태로 데이터가 내려온다
                //이를 식별값 조건으로 주소록 앱에 필요한 데이터를 구체적으로 요청해야한다!!
            Log.d(TAG, "result - ${activityResult.data!!.data}")

                val cursor = contentResolver.query(
                    //intent의 data
                    activityResult.data!!.data!!,
                    arrayOf<String>(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    ),
                    null,
                    null,
                    null
                )

                //TODO 왜 특정단말(android 11)에서 계속 0이 나오지?? (android 9는 정상)
                // 권한 허용을 해줘야함..!!
                Log.d(TAG, "cursor size - ${cursor?.count}")

                if (cursor!!.moveToFirst()) {
                    val name = cursor?.getString(0)
                    val phone = cursor?.getString(1)

                    Log.d(TAG, "name - $name and phone - $phone")
                }
            }
        }

    //권한요청용
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                //전화번호가 있는 사람
                val intent =
                    Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
                getActivityResult.launch(intent)
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,  android.Manifest.permission.READ_CONTACTS)) { 
                //이전에 거부한 경우
                Toast.makeText(baseContext, "Permission Necessary!!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(baseContext, "Go to Setting!!", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btShowContact.setOnClickListener {
            val permission = android.Manifest.permission.READ_CONTACTS
            requestPermissionLauncher.launch(permission)
//            getActivityResult.launch(ActivityResultContracts.PickContact().createIntent(baseContext, ContactsContract.CommonDataKinds.Phone.CONTENT_URI))
        }
    }
}