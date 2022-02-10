package com.example.corn_farmer.src.home

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.home.model.MovieDto
import com.example.corn_farmer.src.home.model.MovieResponse
import com.example.corn_farmer.src.search.SearchFragment
import com.example.corn_farmer.src.detail.DetailFragment
import com.example.corn_farmer.src.loading.CustomLoadingDialog
import com.example.corn_farmer.src.wishlist.WishlistActivity
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment(), HomeFragmentView {
    lateinit var binding: FragmentHomeBinding

    var list: List<MovieDto> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loadingAnimDialog = CustomLoadingDialog(requireContext())
        loadingAnimDialog.setCancelable(false)
        loadingAnimDialog.setCanceledOnTouchOutside(false)
        loadingAnimDialog.show()
        Handler().postDelayed({
            loadingAnimDialog.dismiss()
        },2500)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("join",Context.MODE_PRIVATE)
        val serverToken = sharedPreferences?.getString("servertoken","")

        val mActivity = activity as MainActivity //메인 액티비티

        binding.mainSearchIv.setOnClickListener {
            mActivity.callFragment(SearchFragment())
        }

        if(serverToken==""){ //비로그인
            var service = HomeService(this,"")
            service.tryGetMovieList()
        }
        else {
            var service = HomeService(this, serverToken)
            service.tryGetMovieList()
        }

        binding.mainLikeIv.setOnClickListener {
            startActivity(Intent(requireContext(), WishlistActivity::class.java))
        }

        return binding.root
    }

    override fun onGetMovieListSuccess(response: MovieResponse) {
        list = response!!.result!!

        Log.d("HomeFragment", "$list")

        binding.mainLikeIv

        val todayMovieRVAdapter = TodayMovieRVAdapter(list)
        binding.homeOttItemRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.homeOttItemRecyclerview.adapter = todayMovieRVAdapter
        todayMovieRVAdapter.setMyItemClickListener(object : TodayMovieRVAdapter.MyItemClickListener {
            override fun onItemClick(MovieDto: MovieDto, position: Int) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, DetailFragment(MovieDto.movieIdx!!, -1, ""))
                    .commitAllowingStateLoss()
            }
        })
    }

    override fun onGetMovieListFailure(message: String) {
        Log.d("Fail", "작품 정보 가져오기 실패")
    }

}//HomeFragment 끝


