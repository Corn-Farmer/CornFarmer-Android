package com.example.corn_farmer.src.join

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.cornfarmer_android.databinding.ActivityJoinNicknameBinding

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
                if(binding.nicknameNicknameEt.length() < 3 || binding.loginBirthdayEt.length() < 4 || binding.loginBirthdayMonthEt.length() < 2
                    || binding.loginBirthdayDayEt.length() < 2) {
                    binding.nicknameFinishIv.visibility = View.VISIBLE
                    binding.nicknameFinishColorIv.visibility = View.GONE
                }else if(binding.nicknameNicknameEt.length() > 2 && binding.loginBirthdayEt.length() > 3 && binding.loginBirthdayMonthEt.length() > 1
                    && binding.loginBirthdayDayEt.length() > 1){
                    binding.nicknameFinishIv.visibility = View.GONE
                    binding.nicknameFinishColorIv.visibility = View.VISIBLE
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.loginBirthdayEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.nicknameNicknameEt.length() < 3 || binding.loginBirthdayEt.length() < 4 || binding.loginBirthdayMonthEt.length() < 2
                    || binding.loginBirthdayDayEt.length() < 2) {
                    binding.nicknameFinishIv.visibility = View.VISIBLE
                    binding.nicknameFinishColorIv.visibility = View.GONE
                }else if(binding.nicknameNicknameEt.length() > 2 && binding.loginBirthdayEt.length() > 3 && binding.loginBirthdayMonthEt.length() > 1
                    && binding.loginBirthdayDayEt.length() > 1){
                    binding.nicknameFinishIv.visibility = View.GONE
                    binding.nicknameFinishColorIv.visibility = View.VISIBLE
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.loginBirthdayMonthEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.nicknameNicknameEt.length() < 3 || binding.loginBirthdayEt.length() < 4 || binding.loginBirthdayMonthEt.length() < 2
                    || binding.loginBirthdayDayEt.length() < 2) {
                    binding.nicknameFinishIv.visibility = View.VISIBLE
                    binding.nicknameFinishColorIv.visibility = View.GONE
                }else if(binding.nicknameNicknameEt.length() > 2 && binding.loginBirthdayEt.length() > 3 && binding.loginBirthdayMonthEt.length() > 1
                    && binding.loginBirthdayDayEt.length() > 1){
                    binding.nicknameFinishIv.visibility = View.GONE
                    binding.nicknameFinishColorIv.visibility = View.VISIBLE
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.loginBirthdayDayEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.nicknameNicknameEt.length() < 3 || binding.loginBirthdayEt.length() < 4 || binding.loginBirthdayMonthEt.length() < 2
                    || binding.loginBirthdayDayEt.length() < 2) {
                    binding.nicknameFinishIv.visibility = View.VISIBLE
                    binding.nicknameFinishColorIv.visibility = View.GONE
                }else if(binding.nicknameNicknameEt.length() > 2 && binding.loginBirthdayEt.length() > 3 && binding.loginBirthdayMonthEt.length() > 1
                    && binding.loginBirthdayDayEt.length() > 1){
                    binding.nicknameFinishIv.visibility = View.GONE
                    binding.nicknameFinishColorIv.visibility = View.VISIBLE
                }
            }
            override fun afterTextChanged(s: Editable?) {
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


        if (binding.nicknameNicknameEt.text.toString().isEmpty()){
            Toast.makeText(this, "닉네임 형식이 잘못되었습니다.", Toast.LENGTH_LONG).show()
            return
        }

        val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("nickname", binding.nicknameNicknameEt.text.toString())

        if(binding.nicknameMaleRb.isChecked){
            editor.putString("sex", binding.nicknameMaleRb.text.toString())
        }else if(binding.nicknameFemaleRb.isChecked){
            editor.putString("sex", binding.nicknameFemaleRb.text.toString())
        }


        editor.putString("birthday", binding.loginBirthdayEt.text.toString() +
                binding.loginBirthdayMonthEt.text.toString() +
                binding.loginBirthdayDayEt.text.toString())
        editor.commit()

    }




}