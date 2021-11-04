package com.huni.ch13_activity_component

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.huni.ch13_activity_component.databinding.ActivityAddDetailBinding

class AddDetailActivity : AppCompatActivity() {
    val TAG:String = AddDetailActivity::class.java.simpleName

    private lateinit var binding: ActivityAddDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add_detail)

        binding = ActivityAddDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_save -> {
                val intent = intent
                intent.putExtra("result", binding.addEditView.text.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()

                true
            }
        }

        return true
    }
}