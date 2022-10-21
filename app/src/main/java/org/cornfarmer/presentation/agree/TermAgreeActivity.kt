package org.cornfarmer.presentation.agree

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.cornfarmer.R
import org.cornfarmer.databinding.ActivityTermsAgreeBinding
import org.cornfarmer.presentation.join.JoinOttActivity

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

        binding.cbCheckOne.isChecked = isAgreeOne == 1
        binding.cbCheckTwo.isChecked = isAgreeTwo == 1
        if (isAgreeOne == 1 && isAgreeTwo == 1) {
            binding.tvNext.setBackgroundColor(Color.parseColor("#FBD510"))
            isNextPossible = 1
        }
        binding.cbCheckAll.setOnClickListener { onCheckChanged(binding.cbCheckAll) }
        binding.cbCheckOne.setOnClickListener { onCheckChanged(binding.cbCheckOne) }
        binding.cbCheckTwo.setOnClickListener { onCheckChanged(binding.cbCheckTwo) }
        binding.tvNext.setOnClickListener {
            if (isNextPossible == 1) {
                startActivity(Intent(this, JoinOttActivity::class.java))
            }
        }

        binding.agreeCheckOneInfoIv.setOnClickListener {
            startActivity(Intent(this, AgreeOneActivity::class.java))
        }
        binding.cbCheckTwo.setOnClickListener {
            startActivity(Intent(this, AgreeTwoActivity::class.java))
        }
    }

    private fun onCheckChanged(checkBox: CheckBox) {
        when (checkBox) {
            binding.cbCheckAll -> {
                if (binding.cbCheckAll.isChecked) {
                    binding.cbCheckOne.isChecked = true
                    binding.cbCheckTwo.isChecked = true
                } else {
                    binding.cbCheckOne.isChecked = false
                    binding.cbCheckTwo.isChecked = false
                }
            }
            else -> {
                binding.cbCheckAll.isChecked = (
                    binding.cbCheckOne.isChecked && binding.cbCheckTwo.isChecked
                    )
            }
        }
        if (binding.cbCheckAll.isChecked) {
            binding.tvNext.setBackgroundColor(Color.parseColor("#FBD510"))
            isNextPossible = 1
        } else {
            binding.tvNext.setBackgroundColor(Color.parseColor("#D0CECE"))
            isNextPossible = 0
        }
    }
}
