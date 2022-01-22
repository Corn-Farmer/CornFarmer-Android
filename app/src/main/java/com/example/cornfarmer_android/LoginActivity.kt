package com.example.cornfarmer_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cornfarmer_android.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginNoneLoginBt.setOnClickListener {
            val intent = Intent(this, JoinProfileActivity::class.java)
            startActivity(intent)
        }

        binding.loginKakaoLoginBt.setOnClickListener {
            val intent = Intent(this, JoinProfileActivity::class.java)
            startActivity(intent)
        }

        binding.loginNaverLoginBt.setOnClickListener {
            val intent = Intent(this, JoinProfileActivity::class.java)
            startActivity(intent)
        }



    }
}