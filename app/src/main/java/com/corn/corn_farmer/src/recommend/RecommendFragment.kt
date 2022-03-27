package com.corn.corn_farmer.src.recommend

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
import com.corn.corn_farmer.MainActivity
import com.corn.corn_farmer.src.detail.DetailFragment
import com.corn.corn_farmer.src.keyword.KeywordFragment
import com.corn.corn_farmer.src.loading.CustomLoadingDialog
import com.corn.corn_farmer.src.recommend.model.getRecommendMovieAPI
import com.corn.corn_farmer.src.recommend.model.movieInfo
import com.corn.corn_farmer.src.detail.model.putMovieLike
import com.corn.corn_farmer.src.search.SearchFragment
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.FragmentRecommendBinding

class RecommendFragment(var keywordIdx : Int) : Fragment(), RecommendFragmentView {
    lateinit var binding : FragmentRecommendBinding
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
        binding = FragmentRecommendBinding.inflate(inflater, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        val servertoken = sharedPreferences?.getString("servertoken", "")
        Log.d("키워드 토큰",servertoken.toString())
        var service = RecommendService(this, keywordIdx, servertoken!!)
        service.tryGetMovieInfo()

        binding.recommendPreviousBtnIv.setOnClickListener {
            val mActivity = activity as MainActivity //메인 액티비티
            mActivity.callFragment(KeywordFragment())
        }

        binding.recommendSearchBtnIv.setOnClickListener {
            val mActivity = activity as MainActivity //메인 액티비티
            mActivity.callFragment(SearchFragment())
        }

        return binding.root
    }

    override fun onGetRecommendSuccess(response: getRecommendMovieAPI) {
        val result = response.result!!
        val movieInfoList = result.movieList
        val ottRVAdapter = OttRVAdapter(movieInfoList)

        binding.recommendTitleKeywordTv.text = "# ${result.keyword}"
        binding.recommendSubtitleTv.text = "컨파머가 추천하는 ${result.keyword}를(을) 위한 컨텐츠"

        binding.recommendMovieRV.adapter = ottRVAdapter
        binding.recommendMovieRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        ottRVAdapter.setMyItemClickListener(object : OttRVAdapter.MyItemClickListener {
            override fun onItemClick(movieInfo : movieInfo, position: Int) {
                val mActivity = activity as MainActivity //메인 액티비티
                mActivity.callFragment(DetailFragment(movieInfo.movieIdx, keywordIdx, ""))
                Log.d("heart", "itemclick")
            }
        })
    }

    override fun onGetRecommendFailure(message: String) {
        Toast.makeText(context, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onPutMovieLikeSuccess(response: putMovieLike) {
        Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
    }

    override fun onPutMovieLikeFailure(message: String) {
        Toast.makeText(context, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }
}