package org.cornfarmer.persentation.agree

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivityTermsAgreeBinding
import org.cornfarmer.persentation.join.JoinOttActivity

class TermAgreeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTermsAgreeBinding

    var isNextPossible = 0
    var isAgreeOne = 0
    var isAgreeTwo = 0
    var check_cnt = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_agree)
        isAgreeOne = intent.getIntExtra("agreeone", 0)
        isAgreeTwo = intent.getIntExtra("agreetwo", 0)
        val sharedPreferences = getSharedPreferences("agree", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (isAgreeOne == 1) {
            editor.putInt("one", 1)
            editor.apply()
            check_cnt++
        }
        if (isAgreeTwo == 1) {
            editor.putInt("two", 1)
            editor.apply()
            check_cnt++
        }
        isAgreeOne = sharedPreferences.getInt("one", 0)
        isAgreeTwo = sharedPreferences.getInt("two", 0)

        binding.agreeCheckOneCheckbox.isChecked = isAgreeOne == 1
        binding.agreeCheckTwoCheckbox.isChecked = isAgreeTwo == 1
        if (isAgreeOne == 1 && isAgreeTwo == 1) {
            binding.agreeNextTv.setBackgroundColor(Color.parseColor("#FBD510"))
            isNextPossible = 1
        }
        binding.agreeCheckAllCheckbox.setOnClickListener { onCheckChanged(binding.agreeCheckAllCheckbox) }
        binding.agreeCheckOneCheckbox.setOnClickListener { onCheckChanged(binding.agreeCheckOneCheckbox) }
        binding.agreeCheckTwoCheckbox.setOnClickListener { onCheckChanged(binding.agreeCheckTwoCheckbox) }
        binding.agreeNextTv.setOnClickListener {
            if (isNextPossible == 1) {
                startActivity(Intent(this, JoinOttActivity::class.java))
            }
        }

        binding.agreeCheckOneInfoIv.setOnClickListener {
            startActivity(Intent(this, AgreeOneActivity::class.java))
        }
        binding.agreeCheckTwoInfoIv.setOnClickListener {
            startActivity(Intent(this, AgreeTwoActivity::class.java))
        }
    }

    private fun onCheckChanged(checkBox: CheckBox) {
        when (checkBox) {
            binding.agreeCheckAllCheckbox -> {
                if (binding.agreeCheckAllCheckbox.isChecked) {
                    binding.agreeCheckOneCheckbox.isChecked = true
                    binding.agreeCheckTwoCheckbox.isChecked = true
                } else {
                    binding.agreeCheckOneCheckbox.isChecked = false
                    binding.agreeCheckTwoCheckbox.isChecked = false
                }
            }
            else -> {
                binding.agreeCheckAllCheckbox.isChecked = (
                    binding.agreeCheckOneCheckbox.isChecked && binding.agreeCheckTwoCheckbox.isChecked
                    )
            }
        }
        if (binding.agreeCheckAllCheckbox.isChecked) {
            binding.agreeNextTv.setBackgroundColor(Color.parseColor("#FBD510"))
            isNextPossible = 1
        } else {
            binding.agreeNextTv.setBackgroundColor(Color.parseColor("#D0CECE"))
            isNextPossible = 0
        }
    }
}
