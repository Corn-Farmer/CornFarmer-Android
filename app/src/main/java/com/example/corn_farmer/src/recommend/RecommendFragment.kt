package com.example.corn_farmer.src.recommend

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.detail.DetailFragment
import com.example.corn_farmer.src.keyword.KeywordFragment
import com.example.corn_farmer.src.loading.CustomLoadingDialog
import com.example.corn_farmer.src.recommend.model.getRecommendMovieAPI
import com.example.corn_farmer.src.recommend.model.movieInfo
import com.example.corn_farmer.src.recommend.model.putMovieLike
import com.example.corn_farmer.src.search.SearchFragment
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.FragmentRecommendBinding

class RecommendFragment(var keywordIdx : Int) : Fragment(), RecommendFragmentView {
    lateinit var binding : FragmentRecommendBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loadingAnimDialog = CustomLoadingDialog(requireContext())
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
        binding = FragmentRecommendBinding.inflate(inflater, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        val servertoken = sharedPreferences?.getString("servertoken", "")
        Log.d("키워드 토큰",servertoken.toString())
        var service = RecommendService(this, keywordIdx, servertoken!!)
        service.tryGetMovieInfo()

        binding.recommendPreviousBtnIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, KeywordFragment())
                .commitAllowingStateLoss()
        }

        binding.recommendSearchBtnIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, SearchFragment())
                .commitAllowingStateLoss()
        }

        return binding.root
    }

    override fun onGetRecommendSuccess(response: getRecommendMovieAPI) {
        val result = response.result!!
        val movieInfoList = result.movieList
        val ottRVAdapter = OttRVAdapter(movieInfoList)

        binding.recommendTitleKeywordTv.text = "# ${result.keyword}"
        binding.recommendSubtitleTv.text = "컨파머가 추천하는 ${result.keyword}을 위한 컨텐츠"

        binding.recommendMovieRV.adapter = ottRVAdapter
        binding.recommendMovieRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        ottRVAdapter.setMyItemClickListener(object : OttRVAdapter.MyItemClickListener {
            override fun onItemClick(movieInfo : movieInfo, position: Int) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame, DetailFragment(movieInfo.movieIdx, keywordIdx, ""))
                    .commitAllowingStateLoss()
            }
        })

    }

    override fun onGetRecommendFailure(message: String) {
        Log.d("키워드Fail", "실패")
    }

    override fun onPutMovieLikeSuccess(response: putMovieLike) {
        Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
    }

    override fun onPutMovieLikeFailure(message: String) {
        TODO("Not yet implemented")
    }
}