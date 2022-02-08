package com.example.corn_farmer.src.profile_modify

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.corn_farmer.src.profile.ProfileService
import com.example.corn_farmer.src.profile.model.ProfileGenre
import com.example.corn_farmer.src.profile.model.ProfileOtt
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.ActivityProfileModifyBinding

class ProfileModifyActivity : AppCompatActivity() {

    lateinit var binding : ActivityProfileModifyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("join", Context.MODE_PRIVATE)
        val sharedPreferences2 = getSharedPreferences("userinfo", Context.MODE_PRIVATE)

        var gender = sharedPreferences?.getString("isMale",null)
        if(gender == "true"){
            binding.modifyGenderInfoEt.text = "남자"
        }
        else{
            binding.modifyGenderInfoEt.text = "여자"
        }

        val birth = sharedPreferences?.getString("birthday",null)
        binding.modifyBirthInfoEt.text = birth


        //수정할 때 닉네임이랑 사진 다시 하기

        val nickname = sharedPreferences?.getString("nickname",null)



        var ottList : ArrayList<ProfileOtt>





        var genreList : ArrayList<ProfileGenre>

        binding.modifyCancelIv.setOnClickListener {
            finish()
        }
        binding.ottAppleTvIv.setOnClickListener {

        }


    }
}