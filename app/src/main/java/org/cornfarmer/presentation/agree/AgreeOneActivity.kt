package org.cornfarmer.presentation.agree

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.cornfarmer.R
import org.cornfarmer.databinding.ActivityAgreeOneBinding

class AgreeOneActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgreeOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_agree_one)

        binding.ivCancel.setOnClickListener {
            finish()
        }
        binding.tvNext.setOnClickListener {
            val intent = Intent(this, TermAgreeActivity::class.java)
            intent.putExtra("agreeone", 1)
            startActivity(intent)
        }
    }
}
