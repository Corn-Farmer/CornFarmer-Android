package com.example.corn_farmer.src.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.corn_farmer.src.home.model.MovieDto
import com.example.corn_farmer.src.home.model.MovieResponse
import com.example.cornfarmer_android.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), HomeFragmentView {
    lateinit var binding: FragmentHomeBinding

    var list: List<MovieDto> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        var service = HomeService(this)
        service.tryGetMovieList()


        return binding.root
    }

    override fun onGetMovieListSuccess(response: MovieResponse) {
        list = response!!.result!!

        Log.d("HomeFragment", "$list")

        val todayMovieRVAdapter = TodayMovieRVAdapter(list)
        binding.homeOttItemRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.homeOttItemRecyclerview.adapter = todayMovieRVAdapter
    }

    override fun onGetMovieListFailure(message: String) {
        Log.d("Fail", "실패")
    }


}//HomeFragment 끝
