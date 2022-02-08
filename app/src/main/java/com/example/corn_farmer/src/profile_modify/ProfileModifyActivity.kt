package com.example.corn_farmer.src.profile_modify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.ActivityProfileModifyBinding

class ProfileModifyActivity : AppCompatActivity() {

    lateinit var binding : ActivityProfileModifyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
        val nickname = sharedPreferences.getString("nickname", null)
        var sex : String = ""
        val birthday = sharedPreferences.getString("birthday", null)
        val isMale = sharedPreferences.getString("isMale",null)
        val isFemale = sharedPreferences.getString("isFemale",null)

        if(isMale==null){
            sex = "여자"
            Log.d("female","여자")
        }
        else if(isFemale==null){
            sex = "남자"
            Log.d("male","남자")
        }


        binding.modifyNicknameInfoEt.hint = nickname
        binding.modifyGenderInfoEt.hint = sex
        binding.modifyBirthInfoEt.hint = birthday

        binding.modifyCancelIv.setOnClickListener {
            finish()
        }


    }
}