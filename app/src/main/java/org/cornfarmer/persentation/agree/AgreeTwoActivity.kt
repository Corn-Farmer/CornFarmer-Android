package org.cornfarmer.persentation.agree

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivityAgreeTwoBinding

class AgreeTwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgreeTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_agree_two)

        binding.agreeTwoCancelIv.setOnClickListener {
            finish()
        }
        binding.agreeNextTv.setOnClickListener {
            val intent = Intent(this, TermAgreeActivity::class.java)
            intent.putExtra("agreetwo", 1)
            startActivity(intent)
        }
    }
}
