package com.example.corn_farmer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.corn_farmer.Fragment.HomeFragment
import com.example.corn_farmer.Fragment.KeywordFragment
import com.example.corn_farmer.Fragment.ProfileFragment
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) //전체화면

    }

    private fun initNavigation(){
        supportFragmentManager.beginTransaction().replace(R.id.main_frame, HomeFragment()).commitAllowingStateLoss()
        binding.mainKeywordIv.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, KeywordFragment()).commitAllowingStateLoss()
        }
        binding.mainHomeIv.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, HomeFragment()).commitAllowingStateLoss()
        }
        binding.mainProfileIv.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.main_frame, ProfileFragment()).commitAllowingStateLoss()
        }
    }
}