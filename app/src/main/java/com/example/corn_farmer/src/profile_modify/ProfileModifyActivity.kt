package com.example.corn_farmer.src.profile_modify

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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


        

        var nowUserHasgenre = sharedPreferences.getString("genrelist",null)
        nowUserHasgenre = nowUserHasgenre!!.replace("[","").replace("]","").replace(" ","")
        var nowUserHasGenreList : List<String> = nowUserHasgenre.split(",")
        Log.d("장르 리스트 : ",nowUserHasGenreList.toString())

        var nowUserHasOtt = sharedPreferences.getString("ottlist",null)
        nowUserHasOtt = nowUserHasOtt!!.replace("[","").replace("]","").replace(" ","")
        var nowUserHasOttList : List<String> = nowUserHasOtt.split(",") //

        if(nowUserHasOttList.contains("1")){
            binding.ottAppleTvIv.visibility = View.VISIBLE
            binding.ottAppleTvSelectIv.visibility = View.GONE
        }
        else{
            binding.ottAppleTvSelectIv.visibility = View.VISIBLE
            binding.ottAppleTvIv.visibility = View. GONE
        }

        if(nowUserHasOttList.contains("2")){
            binding.ottPrimeVideoIv.visibility = View.VISIBLE
            binding.ottPrimeVideoSelectIv.visibility = View.GONE
        }
        else{
            binding.ottPrimeVideoIv.visibility = View.GONE
            binding.ottPrimeVideoSelectIv.visibility = View.VISIBLE
        }

        if(nowUserHasOttList.contains("3")){
            binding.ottDisneyIv.visibility = View.VISIBLE
            binding.ottDisneySelectIv.visibility = View.GONE
        }
        else{
            binding.ottDisneyIv.visibility = View.GONE
            binding.ottDisneySelectIv.visibility = View.VISIBLE

        }
        if(nowUserHasOttList.contains("4")){
            binding.ottCoupangIv.visibility = View.VISIBLE
            binding.ottCoupangSelectIv.visibility = View.GONE
        }
        else{
            binding.ottCoupangIv.visibility = View.GONE
            binding.ottCoupangSelectIv.visibility = View.VISIBLE

        }
        if(nowUserHasOttList.contains("5")){
            binding.ottWavveIv.visibility = View.VISIBLE
            binding.ottWavveSelectIv.visibility = View.GONE
        }
        else{
            binding.ottWavveIv.visibility = View.GONE
            binding.ottWavveSelectIv.visibility = View.VISIBLE

        }
        if(nowUserHasOttList.contains("6")){
            binding.ottTvingIv.visibility = View.VISIBLE
            binding.ottTvingSelectIv.visibility = View.GONE
        }
        else{
            binding.ottTvingIv.visibility = View.GONE
            binding.ottTvingSelectIv.visibility = View.VISIBLE

        }
        if(nowUserHasOttList.contains("7")){
            binding.ottNetflixIv.visibility = View.VISIBLE
            binding.ottNetflixSelectIv.visibility = View.GONE
        }
        else{
            binding.ottNetflixIv.visibility = View.GONE
            binding.ottNetflixSelectIv.visibility = View.VISIBLE
        }
        if(nowUserHasOttList.contains("8")){
            binding.ottWhatchaIv.visibility = View.VISIBLE
            binding.ottWhatchaSelectIv.visibility = View.GONE
        }
        else{
            binding.ottWhatchaIv.visibility = View.GONE
            binding.ottWhatchaSelectIv.visibility = View.VISIBLE
        }

        binding.modifyCancelIv.setOnClickListener {
            finish()
        }

        binding.ottAppleTvIv.setOnClickListener {

        }
        binding.ottPrimeVideoIv.setOnClickListener {

        }
        binding.ottDisneyIv.setOnClickListener {

        }
        binding.ottCoupangIv.setOnClickListener {

        }
        binding.ottWavveIv.setOnClickListener {

        }

        binding.ottTvingIv.setOnClickListener {

        }
        binding.ottNetflixIv.setOnClickListener {

        }
        binding.ottWhatchaIv.setOnClickListener {

        }
    }

}