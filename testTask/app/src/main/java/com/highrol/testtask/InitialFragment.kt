package com.highrol.testtask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.highrol.testtask.databinding.FragmentInitialBinding


class InitialFragment : Fragment() {
    lateinit var binding : FragmentInitialBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitialBinding.inflate(inflater, container, false)
        return binding.root
    }



}