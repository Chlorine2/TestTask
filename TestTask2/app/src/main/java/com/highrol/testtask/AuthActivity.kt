package com.highrol.testtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportActionBar?.hide()

    }
}