package com.huni.ch11_jetpack_library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.huni.ch11_jetpack_library.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    val TAG : String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btNext.setOnClickListener(this)
        binding.btNextFragment.setOnClickListener(this)
        binding.btNextRecyclerview.setOnClickListener(this)
        binding.btNext.setOnClickListener(this)


//        binding.btNext.setOnClickListener(object: View.OnClickListener{
//            override fun onClick(v: View?) {
//                val intent : Intent = Intent(this@MainActivity, SecondActivity::class.java)
//
//                startActivity(intent)
//            }
//        })
//
//        binding.btNextFragment.setOnClickListener {
//            val intent : Intent = Intent(this@MainActivity, FragmentActivity::class.java)
//
//            startActivity(intent)
//        }
//
//        binding.btNextRecyclerview.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(v: View?) {
//                val intent = Intent(this@MainActivity, RecyclerViewActivity::class.java)
//
//                startActivity(intent)
//            }
//
//        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_next -> {
                Log.d(TAG, "onClick - bt_next")
                val intent : Intent = Intent(this@MainActivity, ToolbarActionbarActivity::class.java)
                startActivity(intent)
            }

            R.id.bt_next_fragment -> {
                Log.d(TAG, "onClick - bt_next_fragment")
                val intent: Intent = Intent(this@MainActivity, FragmentActivity::class.java)
                startActivity(intent)
            }

            R.id.bt_next_recyclerview -> {
                Log.d(TAG, "onClick - bt_next_recyclerview")
                val intent: Intent = Intent(this@MainActivity, RecyclerViewActivity::class.java)
                startActivity(intent)
            }
        }
    }


}