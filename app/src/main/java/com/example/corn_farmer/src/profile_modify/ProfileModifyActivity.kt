package com.example.corn_farmer.src.profile_modify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.ActivityProfileModifyBinding

class ProfileModifyActivity : AppCompatActivity() {

    lateinit var binding : ActivityProfileModifyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.modifyCancelIv.setOnClickListener {
            finish()
        }


    }
}