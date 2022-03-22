package com.corn.corn_farmer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.corn.corn_farmer.config.Application
import com.corn.corn_farmer.src.detail.DetailFragment
import com.corn.corn_farmer.src.home.HomeFragment
import com.corn.corn_farmer.src.keyword.KeywordFragment
import com.corn.corn_farmer.src.profile.ProfileFragment
import com.corn.corn_farmer.src.home.model.MovieDto
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivityMainBinding
import java.io.Serializable

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    var waitTime = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initNavigation() // 화면 설정
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        ) //전체화면

        if (intent.getIntExtra("movieIdx", -1)!! > 0) {
            callFragment(DetailFragment(intent.getIntExtra("movieIdx", 1), -1, ""))
        }


    } //onCreate


    private fun initNavigation() {
        callFragment(HomeFragment())

        binding.mainKeywordIv.setOnClickListener {
            callFragment(KeywordFragment())
        }
        binding.mainHomeIv.setOnClickListener {
            callFragment(HomeFragment())
        }
        binding.mainProfileIv.setOnClickListener {
            val sharedPreferences = Application.joinSharedPreferences
            val serverToken = sharedPreferences.getString("servertoken","")
            if(serverToken==""){
                Toast.makeText(this,"로그인이 필요한 서비스입니다.",Toast.LENGTH_SHORT).show()

            }
            else {
                callFragment(ProfileFragment())
            }
        }
    }

    fun giveMovieDataToFragment(fragment:Fragment,list : List<MovieDto>){
        //번들로 HomeFragment에 데이터 넘겨주기
        val bundle = Bundle()
        bundle.putSerializable("movieList",(list as Serializable))

        fragment.arguments = bundle
        callFragment(fragment)
    }
    fun callFragment(fragment:Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame,fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() - waitTime >=1500 ) {
            waitTime = System.currentTimeMillis()
            Toast.makeText(this,"뒤로가기 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show()
        } else {
            finish() // 액티비티 종료
        }
    }


    override fun onStop() {
        super.onStop()
//        finish()
    }

}