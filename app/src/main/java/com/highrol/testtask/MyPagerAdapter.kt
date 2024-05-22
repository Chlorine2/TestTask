package com.highrol.testtask

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = mutableListOf<Fragment>()
    private val fragmentTitleList = mutableListOf<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
        notifyDataSetChanged()
    }
    fun deleteFragment(position : Int) {
        if (position < fragmentList.size) {
            fragmentList.removeAt(position)
            fragmentTitleList.removeAt(position)
            notifyDataSetChanged()
        }
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }
}