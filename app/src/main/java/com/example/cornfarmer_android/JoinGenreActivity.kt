package com.example.cornfarmer_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.corn_farmer.MainActivity
import com.example.cornfarmer_android.databinding.ActivityJoinGenreBinding

class JoinGenreActivity : AppCompatActivity() {

    private lateinit var binding : ActivityJoinGenreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.genreBackIv.setOnClickListener {
            finish()
        }

        binding.genreFinishColorIv.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}