package com.huni.ch16_content_provider.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.huni.ch16_content_provider.R
import com.huni.ch16_content_provider.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {
    companion object {
        val TAG : String = ContactActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityContactBinding

    private val getActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val result: String? = it.data?.dataString
            //url 형태로 데이터가 내려온다
            //이를 식별값 조건으로 주로속 앱에 필요한 데이터를 구체적으로 요청해야한다!!
            Log.d(TAG, "result - $result")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btShowContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            getActivityResult.launch(intent)

        }

    }


}