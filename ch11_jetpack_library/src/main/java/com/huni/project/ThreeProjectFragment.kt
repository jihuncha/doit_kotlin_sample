package com.huni.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huni.ch11_jetpack_library.databinding.FragmentProjectThreeBinding

class ThreeProjectFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentProjectThreeBinding.inflate(inflater, container, false).root
    }
}