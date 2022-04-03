package com.corn.corn_farmer.src.join

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivityAgreeOneBinding

class AgreeOneActivity :AppCompatActivity() {
    private lateinit var binding : ActivityAgreeOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_agree_one)

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