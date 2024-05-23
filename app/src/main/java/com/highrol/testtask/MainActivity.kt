package com.highrol.testtask

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.highrol.testtask.Database.PreferencesManager
import com.highrol.testtask.interfaces.FragmentInteractionListener

class MainActivity : AppCompatActivity(), FragmentInteractionListener {

    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: MyPagerAdapter
    lateinit var notificationManager: NotificationManager
    lateinit var preferencesManager: PreferencesManager

    private val channelId = "i.apps.notifications"
    private val description = "Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        pagerAdapter = MyPagerAdapter(supportFragmentManager)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        preferencesManager = PreferencesManager(application)

        viewPager.adapter = pagerAdapter

        val length = preferencesManager.getData("pages_count", 1)

        for (i in 1..length) {
            pagerAdapter.addFragment(MyFragment.newInstance(i.toString()), i.toString())
        }
        viewPager.currentItem = pagerAdapter.count - 1

        val notificationIntent2 = intent
        val fragmentPosition = notificationIntent2.getIntExtra("1", -1)
        if (fragmentPosition != -1) {
            viewPager.currentItem = fragmentPosition - 1
        }

    }


    override fun onAddNewFragment() {
        val newCount = pagerAdapter.count + 1
        pagerAdapter.addFragment(MyFragment.newInstance(newCount.toString()), "$newCount")
        viewPager.currentItem = pagerAdapter.count - 1
        preferencesManager.saveData("pages_count", pagerAdapter.count)

    }
    override fun onRemoveFragment(position: Int) {
        pagerAdapter.deleteFragment(pagerAdapter.count - 1)
        viewPager.currentItem = pagerAdapter.count - 1
        preferencesManager.saveData("pages_count", pagerAdapter.count)
        notificationManager.cancel(pagerAdapter.count + 1);
    }

    override fun sendNotification(position : Int) {


        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.putExtra("1", position)

        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH).apply {
                lightColor = Color.GREEN
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(notificationChannel)

            val builder = Notification.Builder(this, channelId)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle("You create notification")
                .setContentText("Notification $position")
                .setContentIntent(pendingIntent)


            notificationManager.notify(position, builder.build())


        } else {

            val builder = Notification.Builder(this)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentText("Notification $position")
                .setContentIntent(pendingIntent)

            notificationManager.notify(1, builder.build())

        }


    }

}