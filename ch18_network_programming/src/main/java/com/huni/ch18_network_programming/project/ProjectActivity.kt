package com.huni.ch18_network_programming.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.ch18_network.RetrofitFragment
import com.example.ch18_network.VolleyFragment
import com.huni.ch18_network_programming.R
import com.huni.ch18_network_programming.databinding.ActivityProjectBinding

class ProjectActivity : AppCompatActivity() {
    companion object {
        val TAG = ProjectActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityProjectBinding
    lateinit var volleyFragment: VolleyFragment
    lateinit var retrofitFragment: RetrofitFragment
    var mode = "volley"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        volleyFragment= VolleyFragment()
        retrofitFragment= RetrofitFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_content, volleyFragment)
            .commit()
        supportActionBar?.title="Volley Test"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId === R.id.menu_volley && mode !== "volley"){
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_content, volleyFragment)
                .commit()
            mode="volley"
            supportActionBar?.title="Volley Test"
        }else if(item.itemId === R.id.menu_retrofit && mode !== "retrofit"){
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_content, retrofitFragment)
                .commit()
            mode="retrofit"
            supportActionBar?.title="Retrofit Test"
        }
        return super.onOptionsItemSelected(item)
    }
}