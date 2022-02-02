package com.example.corn_farmer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.corn_farmer.src.home.HomeFragment
import com.example.corn_farmer.src.keyword.KeywordFragment
import com.example.corn_farmer.src.profile.ProfileFragment
import com.example.corn_farmer.Retrofit.RetrofitBuilder
import com.example.corn_farmer.src.keyword.model.KeywordDto
import com.example.corn_farmer.src.keyword.model.KeywordResponse
import com.example.corn_farmer.src.home.model.MovieDto
import com.example.corn_farmer.src.search.SearchFragment
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation() // 화면 설정
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        ) //전체화면

        //키워드 API 받아오기
        RetrofitBuilder.api
            .getKeywordList()
            .enqueue(object : Callback<KeywordResponse> {
                override fun onResponse(call: Call<KeywordResponse>, response: Response<KeywordResponse>) {
                    val keywordResponse = response.body()
                    val list: List<KeywordDto> = keywordResponse!!.result!!
                    Log.d("My", "$list")
                }

                override fun onFailure(call: Call<KeywordResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    Log.d("Fail", "Fail")
                }


            })
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
            callFragment(SearchFragment())
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
}