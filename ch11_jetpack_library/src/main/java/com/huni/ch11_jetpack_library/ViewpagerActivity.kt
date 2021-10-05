package com.huni.ch11_jetpack_library

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.huni.ch11_jetpack_library.databinding.ActivityViewpagerBinding
import com.huni.ch11_jetpack_library.databinding.ViewpagerItemBinding

class ViewpagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityViewpagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //RecyclerView adapter
//        binding.viewpager.adapter = MyPagerAdapter(datas = arrayListOf("1","2","3"))

        //FragmentState adapter
        binding.viewpager.adapter = MyFragmentPagerAdapter(this)
    }
}

//RecyclerView Adapter
class MyPagerViewHolder(val binding : ViewpagerItemBinding) : RecyclerView.ViewHolder(binding.root)

class MyPagerAdapter(val datas : MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyPagerViewHolder(ViewpagerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyPagerViewHolder).binding

        binding.viewpagerItemData.text = datas[position]
        when (position % 3) {
            0 -> binding.viewpagerItemRoot.setBackgroundColor(Color.RED)
            1 -> binding.viewpagerItemRoot.setBackgroundColor(Color.BLUE)
            2 -> binding.viewpagerItemRoot.setBackgroundColor(Color.GREEN)
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}

class MyFragmentPagerAdapter(activity: ViewpagerActivity): FragmentStateAdapter(activity) {
    val fragments: List<Fragment>
    init {
        fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}




