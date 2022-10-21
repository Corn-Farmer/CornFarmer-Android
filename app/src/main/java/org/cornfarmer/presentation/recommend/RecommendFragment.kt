package org.cornfarmer.presentation.recommend

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.cornfarmer.R
import org.cornfarmer.data.model.request.RequestMovieLike
import org.cornfarmer.data.model.response.MovieInfo
import org.cornfarmer.data.model.response.ResponseRecommend
import org.cornfarmer.data.repository.RecommendService
import org.cornfarmer.data.view.RecommendFragmentView
import org.cornfarmer.databinding.FragmentRecommendBinding
import org.cornfarmer.presentation.detail.DetailFragment
import org.cornfarmer.presentation.keyword.KeywordFragment
import org.cornfarmer.presentation.loading.CustomLoadingDialog
import org.cornfarmer.presentation.main.MainActivity
import org.cornfarmer.presentation.search.SearchFragment
import org.cornfarmer.util.ext.showToast

class RecommendFragment(var keywordIdx: Int) : Fragment(), RecommendFragmentView {
    private lateinit var binding: FragmentRecommendBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loadingAnimDialog = CustomLoadingDialog(requireContext())
        loadingAnimDialog.setCancelable(false)
        loadingAnimDialog.setCanceledOnTouchOutside(false)
        loadingAnimDialog.show()
        Handler().postDelayed({
            loadingAnimDialog.dismiss()
        }, 2500)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recommend, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        val servertoken = sharedPreferences?.getString("servertoken", "")
        Log.d("키워드 토큰", servertoken.toString())
        val service = RecommendService(this, keywordIdx, servertoken!!)
        service.tryGetMovieInfo()

        binding.ivBack.setOnClickListener {
            val mActivity = activity as MainActivity // 메인 액티비티
            mActivity.callFragment(KeywordFragment())
        }

        binding.ivSearch.setOnClickListener {
            val mActivity = activity as MainActivity // 메인 액티비티
            mActivity.callFragment(SearchFragment())
        }

        return binding.root
    }

    override fun onGetRecommendSuccess(response: ResponseRecommend) {
        val result = response.result
        val movieInfoList = result.movieList
        val ottRVAdapter = OttRVAdapter(movieInfoList)

        binding.tvKeyword.text = "# ${result.keyword}"
        binding.tvSubtitle.text = "컨파머가 추천하는 ${result.keyword}를(을) 위한 컨텐츠"

        binding.rvRecommend.adapter = ottRVAdapter
        binding.rvRecommend.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        ottRVAdapter.setMyItemClickListener(object : OttRVAdapter.MyItemClickListener {

            override fun onItemClick(movieInfo: MovieInfo, position: Int) {
                val mActivity = activity as MainActivity // 메인 액티비티
                mActivity.callFragment(DetailFragment(movieInfo.movieIdx, keywordIdx, ""))
                Log.d("heart", "itemclick")
            }
        })
    }

    override fun onGetRecommendFailure(message: String) {
        activity?.showToast("네트워크 연결에 실패했습니다.")
    }

    override fun onPutMovieLikeSuccess(response: RequestMovieLike) {
        activity?.showToast(response.message)
    }

    override fun onPutMovieLikeFailure(message: String) {
        activity?.showToast("네트워크 연결에 실패했습니다.")
    }
}
