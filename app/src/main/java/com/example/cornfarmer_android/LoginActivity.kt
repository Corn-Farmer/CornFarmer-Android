package com.example.cornfarmer_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cornfarmer_android.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {


    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.loginKakaoLoginBt.setOnClickListener {
//            val intent = Intent(this, JoinProfileActivity::class.java)
//            startActivity(intent)
//        }



    }
}