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
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment(), HomeFragmentView {
    lateinit var binding: FragmentHomeBinding

    var list: List<MovieDto> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val mActivity = activity as MainActivity //메인 액티비티

        binding.mainSearchIv.setOnClickListener {
            mActivity.callFragment(SearchFragment())
        }
        binding.mainBackIv.setOnClickListener {
            val loadingAnimDialog = CustomLoadingDialog(requireContext())
            loadingAnimDialog.show()
            Handler().postDelayed({
                loadingAnimDialog.dismiss()
            },5000)
        }

        var service = HomeService(this)
        service.tryGetMovieList()

        return binding.root
    }

    override fun onGetMovieListSuccess(response: MovieResponse) {
        list = response!!.result!!

        Log.d("HomeFragment", "$list")

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


