package com.huni.ch11_jetpack_library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.huni.ch11_jetpack_library.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val TAG : String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btNext.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val intent : Intent = Intent(this@MainActivity, SecondActivity::class.java)

                startActivity(intent)
            }
        })

        binding.btNextFragment.setOnClickListener {
            val intent : Intent = Intent(this@MainActivity, FragmentActivity::class.java)

            startActivity(intent)
        }

        binding.btNextRecyclerview.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(this@MainActivity, RecyclerViewActivity::class.java)

                startActivity(intent)
            }

        })
    }
}