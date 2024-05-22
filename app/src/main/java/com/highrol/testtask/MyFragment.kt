    package com.highrol.testtask
    
    import android.app.Notification
    import android.app.NotificationChannel
    import android.app.NotificationManager
    import android.content.Context
    import android.graphics.BitmapFactory
    import android.graphics.Color
    import android.os.Build
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.View.INVISIBLE
    import android.view.ViewGroup
    import android.widget.Button
    import android.widget.TextView
    import androidx.core.content.ContextCompat.getSystemService
    import androidx.fragment.app.Fragment
    import com.highrol.testtask.interfaces.FragmentInteractionListener
    
    class MyFragment : Fragment() {
    
        private var listener: FragmentInteractionListener? = null
        lateinit var notificationManager: NotificationManager
        lateinit var notificationChannel: NotificationChannel
        lateinit var builder: Notification.Builder
        private val channelId = "i.apps.notifications"
        private val description = "Test notification"
    
        companion object {
            fun newInstance(title: String): MyFragment {
                val fragment = MyFragment()
                val args = Bundle()
                args.putString("title", title)
                fragment.arguments = args
                return fragment
            }
        }
    
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_initial, container, false)
            // Initialize your view components here
            return view
        }
    
        override fun onAttach(context: Context) {
            super.onAttach(context)
            if (context is FragmentInteractionListener) {
                listener = context
            } else {
                throw RuntimeException("$context must implement FragmentInteractionListener")
            }
        }
    
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
    
            val count = arguments?.getString("title")
            view.findViewById<TextView>(R.id.textView).text = count
                notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
    
    
            if(count?.toInt()!! == 1) {
                view.findViewById<Button>(R.id.minus).visibility = INVISIBLE
            }
            view.findViewById<Button>(R.id.plus).setOnClickListener {
                listener?.onAddNewFragment()
            }
            view.findViewById<Button>(R.id.minus).setOnClickListener {
                listener?.onRemoveFragment(count.toInt()-1)
            }
    
            view.findViewById<Button>(R.id.notification).setOnClickListener {
                sendNotification(count.toInt())
            }
        }
    
        private fun sendNotification(position : Int) {
            listener?.sendNotification(position)
        }
    
        override fun onDetach() {
            super.onDetach()
            listener = null
        }
    }