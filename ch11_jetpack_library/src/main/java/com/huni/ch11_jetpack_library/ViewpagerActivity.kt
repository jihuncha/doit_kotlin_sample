package com.huni.ch11_jetpack_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huni.ch11_jetpack_library.databinding.ActivityViewpagerBinding
import com.huni.ch11_jetpack_library.databinding.ViewpagerItemBinding

class ViewpagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityViewpagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewpager.adapter = MyAdapter(datas = arrayListOf("1","2","3"))
    }
}

class MyPagerViewHolder(val binding : ViewpagerItemBinding) : RecyclerView.ViewHolder(binding.root)

class MyPagerAdapter(val datas : MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyPagerViewHolder(ViewpagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyPagerViewHolder).binding
    }

    override fun getItemCount(): Int {
        return datas.size
    }

}




