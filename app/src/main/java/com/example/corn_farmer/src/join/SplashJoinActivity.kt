package com.example.corn_farmer.src.join

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cornfarmer_android.databinding.ActivitySplashJoinBinding
import android.os.Handler
import android.widget.Toast
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
        binding.splashJoinImageIv.setImageURI(Uri.parse(photo))

        Toast.makeText(this, photo.toString(), Toast.LENGTH_SHORT).show()


//        Handler().postDelayed({
//
//
//
//            val intent = Intent(this, JoinOttActivity::class.java)
//            startActivity(intent)
//            finish()
//
//        }, 3000)
    }


}