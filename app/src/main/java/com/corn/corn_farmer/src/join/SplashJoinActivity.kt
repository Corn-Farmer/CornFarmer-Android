package com.corn.corn_farmer.src.join

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.corn.cornfarmer_android.databinding.ActivitySplashJoinBinding
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.corn.corn_farmer.MainActivity
import com.corn.corn_farmer.config.Application
import com.corn.cornfarmer_android.R


class SplashJoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashJoinBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_join)

        val sharedPreferences = Application.joinSharedPreferences
        val nickname = sharedPreferences.getString("nickname", null)
        val photo = sharedPreferences.getString("photo", null)
        binding.splashJoinNicknameTv.text = nickname.toString()

        if(photo == null){
            binding.splashJoinImageIv.setImageResource(R.drawable.cornfarmerprofile)
        }else{
            binding.splashJoinImageIv.setImageURI(Uri.parse(photo))
        }




        Handler().postDelayed({

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }, 3000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }



}