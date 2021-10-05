package com.huni.ch11_jetpack_library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.huni.ch11_jetpack_library.databinding.ActivityFragmentBinding
import com.huni.ch11_jetpack_library.databinding.FragmentViewBinding

class FragmentActivity : AppCompatActivity() {
    val TAG: String = FragmentActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityFragmentBinding
        binding = ActivityFragmentBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //fragment
        val fragmentManager:FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragment = OneFragment()
        transaction.add(R.id.fragment_content, fragment)
        transaction.commit()

        //중요
        //backstack 으로 사용하면 view 재사용 - onDestroyView 까지 사용 가능
        //null 인경우 사용안함
//        transaction.addToBackStack(null)
    }
}

open class OneFragment: Fragment() {
    lateinit var binding: FragmentViewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}

class TwoFragment: OneFragment() {}
class ThreeFragment: OneFragment() {}