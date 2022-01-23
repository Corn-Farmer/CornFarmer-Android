package com.example.cornfarmer_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashJoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_join)

        Handler().postDelayed({
            val intent = Intent(this, JoinOttActivity::class.java)
            startActivity(intent)
            finish()

        }, 3000)
    }
}