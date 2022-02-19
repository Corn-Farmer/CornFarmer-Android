package com.example.corn_farmer.src.join

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cornfarmer_android.databinding.ActivitySplashJoinBinding
import android.os.Handler
import android.widget.Toast
import com.example.corn_farmer.MainActivity
import com.example.cornfarmer_android.R


class SplashJoinActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
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