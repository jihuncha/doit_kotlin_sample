package com.huni.ch13_activity_component

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.huni.ch13_activity_component.databinding.ActivityBundleTestBinding

class BundleTestActivity : AppCompatActivity() {
    val TAG:String = BundleTestActivity::class.java.simpleName

    private lateinit var binding: ActivityBundleTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_bundle_test)

        binding = ActivityBundleTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d(TAG, "onRestoreInstanceState")

        super.onRestoreInstanceState(savedInstanceState)
        
        val data1 = savedInstanceState.getString("data1")

        Log.d(TAG, "check - ${data1}" )
        binding.tvText.text = data1
    }

    //번들에 데이터 저장
    //onStop 다음에 호출된다.
    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState")

        super.onSaveInstanceState(outState)
        outState.putString("data1", "test")
    }
}