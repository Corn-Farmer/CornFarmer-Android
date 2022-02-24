package com.corn.corn_farmer.src.join

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.corn.cornfarmer_android.databinding.ActivityAgreeTwoBinding

class AgreeTwoActivity :AppCompatActivity() {
    lateinit var binding : ActivityAgreeTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgreeTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.agreeTwoCancelIv.setOnClickListener {
            finish()
        }
        binding.agreeNextTv.setOnClickListener {
            val intent = Intent(this, TermAgreeActivity::class.java)
            intent.putExtra("agreetwo",1)
            startActivity(intent)
        }
    }
}