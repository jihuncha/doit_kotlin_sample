package com.huni.ch13_activity_component

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.huni.ch13_activity_component.databinding.ActivityBundleTestBinding

// 번들 test 및 softKeyboard 제어 관련
class BundleTestActivity : AppCompatActivity() {
    val TAG:String = BundleTestActivity::class.java.simpleName

    private lateinit var binding: ActivityBundleTestBinding
    private lateinit var manager: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_bundle_test)

        binding = ActivityBundleTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        //토글
//        binding.btToggleFocus.setOnClickListener{
//            manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
//        }

        //show
        binding.btToggleFocus.setOnClickListener{
            binding.etEditText.requestFocus()
            manager.showSoftInput(binding.etEditText, InputMethodManager.SHOW_IMPLICIT)
        }

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