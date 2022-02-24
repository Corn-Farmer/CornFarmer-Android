package com.corn.corn_farmer.src.join

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.corn.cornfarmer_android.databinding.ActivityAgreeOneBinding

class AgreeOneActivity :AppCompatActivity() {
    lateinit var binding : ActivityAgreeOneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgreeOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.agreeOneCancelIv.setOnClickListener {
            finish()
        }
        binding.agreeNextTv.setOnClickListener {
            val intent = Intent(this, TermAgreeActivity::class.java)
            intent.putExtra("agreeone",1)
            startActivity(intent)
        }
    }
}