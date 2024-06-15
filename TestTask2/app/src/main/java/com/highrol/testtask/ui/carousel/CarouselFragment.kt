package com.highrol.testtask.ui.carousel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.highrol.testtask.R
import com.highrol.testtask.databinding.FragmentAlarmBinding
import com.highrol.testtask.databinding.FragmentCarouselBinding

class CarouselFragment : Fragment() {

    private var _binding: FragmentCarouselBinding? = null
    private var index: Int = -1
    private lateinit var slideIn: Animation
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarouselBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleList = listOf<String>("Water Tracking", "Set Reminder", "Rich your Goals", "Become PRO")
        val descriptionList = listOf<String>("Keep your hydration on point with our easy-to-use tracking features! \uD83D\uDEB0",
            "Set reminders to hydrate regularly and keep your body happy and healthy! ⏰",
            "Set your hydration goals and achieve them with our supportive features. You got this! \uD83E\uDD47",
            "Upgrade to PRO for advanced features and deeper hydration insights. Go PRO! \uD83D\uDE80")

        slideIn = AnimationUtils.loadAnimation(context, R.anim.slide_left)

        val viewFlipper = binding.viewFlipper
        val titleText = binding.slideTitleText
        val descriptionText = binding.slideDescriptionText
        val button = binding.button
        val buttonSkip = binding.buttonSkip

        button.setOnClickListener {
            if(viewFlipper.displayedChild == 4){
                index = -1
            }
            if(viewFlipper.displayedChild == 3){
                buttonSkip.visibility = VISIBLE
                button.text = "Get Pro"

            }


            titleText.text = titleList[++index]
            descriptionText.text = descriptionList[index]
            slideInText(titleText)
            slideInText(descriptionText)
            viewFlipper.showNext();

            if(viewFlipper.displayedChild == 0){
                findNavController().navigate(R.id.action_carouselFragment_to_regestrationFragment)
            }
        }
        buttonSkip.setOnClickListener {
            findNavController().navigate(R.id.action_carouselFragment_to_regestrationFragment)

        }


    }
    private fun slideInText(textView : TextView) {
        textView.startAnimation(slideIn)
        textView.visibility = View.VISIBLE
    }


}