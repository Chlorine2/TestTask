package com.highrol.testtask.interfaces

interface FragmentInteractionListener {
    fun onAddNewFragment()
    fun onRemoveFragment(position : Int)
    fun sendNotification(position: Int)
}