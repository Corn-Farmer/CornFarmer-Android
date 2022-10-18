package org.cornfarmer.persentation.main

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivityMainBinding
import org.cornfarmer.di.Application
import org.cornfarmer.persentation.detail.DetailFragment
import org.cornfarmer.persentation.home.HomeFragment
import org.cornfarmer.persentation.keyword.KeywordFragment
import org.cornfarmer.persentation.profile.ProfileFragment
import org.cornfarmer.util.ext.showToast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var waitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        callFragment(HomeFragment())

        initNavigation() // 화면 설정
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        ) // 전체화면

        if (intent.getIntExtra("movieIdx", -1) > 0) {
            callFragment(DetailFragment(intent.getIntExtra("movieIdx", 1), -1, ""))
        }
    } // onCreate

    private fun initNavigation() {
        binding.mainKeywordIv.setOnClickListener {
            callFragment(KeywordFragment())
        }
        binding.mainHomeIv.setOnClickListener {
            callFragment(HomeFragment())
        }
        binding.mainProfileIv.setOnClickListener {
            val sharedPreferences = Application.joinSharedPreferences
            val serverToken = sharedPreferences.getString("servertoken", "")
            if (serverToken == "") {
                showToast("로그인이 필요한 서비스입니다.")
            } else {
                callFragment(ProfileFragment())
            }
        }
    }

    fun callFragment(fragment: Fragment) {
        if (supportFragmentManager.backStackEntryCount != 1) {
            supportFragmentManager.popBackStackImmediate(
                "$fragment",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, fragment).addToBackStack("$fragment")
        transaction.commit()
    }

    override fun onBackPressed() {
        Log.d("count", supportFragmentManager.backStackEntryCount.toString())
        if (supportFragmentManager.backStackEntryCount == 1) {
            if (System.currentTimeMillis() - waitTime >= 1500) {
                waitTime = System.currentTimeMillis()
                showToast("뒤로가기 버튼을 한번 더 누르면 종료됩니다.")
            } else {
                finish() // 액티비티 종료
            }
        } else {
            super.onBackPressed()
        }
    }
}
