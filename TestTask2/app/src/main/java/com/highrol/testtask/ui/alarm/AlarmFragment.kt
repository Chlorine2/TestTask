package com.highrol.testtask.ui.alarm

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.highrol.testtask.R
import com.highrol.testtask.databinding.FragmentAlarmBinding

class AlarmFragment : Fragment() {

    private var _binding: FragmentAlarmBinding? = null

    private lateinit var scaleOut: Animation
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toggleButton = view.findViewById<ToggleButton>(R.id.toggleButton)
        scaleOut = AnimationUtils.loadAnimation(context, R.anim.scale_animation)

        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.imageView3.animate().translationY(0f)
                binding.imageView3.animate().scaleX(0f)
                binding.imageView3.animate().scaleY(0f)
                binding.cardView2.animate().translationY(0.0f)
                binding.cardView3.animate().translationY(0.0f)
                toggleButton.setBackgroundResource(R.drawable.onn)
                binding.cardView2.foreground=ColorDrawable(Color.parseColor("#006F6F6F"))
                binding.cardView3.foreground=ColorDrawable(Color.parseColor("#006F6F6F"))

                binding.dropdownStart.isEnabled = true
                binding.dropdownEnd.isEnabled = true
                binding.dropdownPeriod.isEnabled = true
            } else {
                binding.imageView3.animate().translationY(200.0f)
                binding.imageView3.animate().scaleX(8.5f)
                binding.imageView3.animate().scaleY(9.0f)
                binding.cardView2.animate().translationY(370.0f)
                binding.cardView3.animate().translationY(370.0f)

                toggleButton.setBackgroundResource(R.drawable.off)
                binding.cardView2.foreground=ColorDrawable(Color.parseColor("#906F6F6F"))
                binding.cardView3.foreground=ColorDrawable(Color.parseColor("#906F6F6F"))

                binding.dropdownStart.isEnabled = false
                binding.dropdownEnd.isEnabled = false
                binding.dropdownPeriod.isEnabled = false

            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}