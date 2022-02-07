package com.huni.ch19_location_infomation.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huni.ch19_location_infomation.databinding.ActivityProjectBinding

class ProjectActivity : AppCompatActivity() {
    companion object {
        val TAG : String = ProjectActivity::class.java.simpleName
    }

    private lateinit var binding : ActivityProjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    //요즘잘안하네..
}