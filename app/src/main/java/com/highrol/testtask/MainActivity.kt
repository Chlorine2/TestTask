package com.highrol.testtask

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import androidx.viewpager.widget.ViewPager
import com.highrol.testtask.Database.PreferencesManager
import com.highrol.testtask.interfaces.FragmentInteractionListener

class MainActivity : AppCompatActivity(), FragmentInteractionListener {

    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: MyPagerAdapter
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var preferencesManager: PreferencesManager

    lateinit var builder: Notification.Builder
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

        val notificationIntent = intent
        val fragmentPosition = notificationIntent.getIntExtra("fragment_position", -1)

        if (fragmentPosition != -1) {
            viewPager.currentItem = fragmentPosition-1
        }

    }

    override fun onAddNewFragment() {
        val newCount = pagerAdapter.count + 1
        pagerAdapter.addFragment(MyFragment.newInstance(newCount.toString()), "$newCount")
        viewPager.currentItem = pagerAdapter.count - 1
        preferencesManager.saveData("pages_count", pagerAdapter.count)

    }
    override fun onRemoveFragment(position: Int) {
        pagerAdapter.deleteFragment(position)
        viewPager.currentItem = pagerAdapter.count - 1
        preferencesManager.saveData("pages_count", pagerAdapter.count)
    }

    override fun sendNotification(position : Int) {


        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.putExtra("fragment_position", position)

        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle("You create notification")
                .setContentText("Notification $position")
                .setContentIntent(pendingIntent)


        } else {

            builder = Notification.Builder(this)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentText("Notification $position")
                .setContentIntent(pendingIntent)

        }
        notificationManager.notify(1, builder.build())

    }

}