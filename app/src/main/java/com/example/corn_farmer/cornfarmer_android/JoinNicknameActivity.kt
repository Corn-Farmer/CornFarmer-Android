package com.example.cornfarmer_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.cornfarmer_android.databinding.ActivityJoinNicknameBinding
import com.example.cornfarmer_android.databinding.ActivityJoinProfileBinding
import java.util.regex.Pattern

class JoinNicknameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinNicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityJoinNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.nicknameFinishColorIv.setOnClickListener {
            signUp()
            val intent = Intent(this, SplashJoinActivity::class.java)
            startActivity(intent)
        }

        binding.nicknameBackIv.setOnClickListener {
            finish()
        }

        binding.nicknameNicknameEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                if(binding.nicknameNicknameEt.length() == 0){
                    binding.nicknameFinishIv.visibility = View.VISIBLE
                    binding.nicknameFinishColorIv.visibility = View.GONE
                }else if(binding.nicknameNicknameEt.length() > 2){
                    binding.nicknameFinishIv.visibility = View.GONE
                    binding.nicknameFinishColorIv.visibility = View.VISIBLE
                }

            }

        })

    }

    private fun signUp(){

        if (binding.loginBirthdayEt.text.toString().isEmpty()
            || binding.loginBirthdayMonthEt.text.toString().isEmpty()
            || binding.loginBirthdayDayEt.text.toString().isEmpty()){
            Toast.makeText(this, "생녈월일 형식이 잘못되었습니다.", Toast.LENGTH_LONG).show()
            return
        }

        if(binding.nicknameSexEt.text.toString().isEmpty()){
            Toast.makeText(this, "성별 형식이 잘못되었습니다", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.nicknameNicknameEt.text.toString().isEmpty()){
            Toast.makeText(this, "닉네임 형식이 잘못되었습니다.", Toast.LENGTH_LONG).show()
            return
        }

        val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("nickname", binding.nicknameNicknameEt.text.toString())
        editor.putString("sex", binding.nicknameSexEt.text.toString())
        editor.putString("birthday", binding.loginBirthdayEt.text.toString() +
                binding.loginBirthdayMonthEt.text.toString() +
                binding.loginBirthdayDayEt.text.toString())
        editor.commit()

    }




}