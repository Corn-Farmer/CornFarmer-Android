package org.cornfarmer.persentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.FragmentHomeBinding
import org.cornfarmer.data.model.response.MovieDto
import org.cornfarmer.data.model.response.MovieResponse
import org.cornfarmer.data.repository.HomeService
import org.cornfarmer.data.view.HomeFragmentView
import org.cornfarmer.persentation.detail.DetailFragment
import org.cornfarmer.persentation.home.adapter.TodayMovieRVAdapter
import org.cornfarmer.persentation.loading.CustomLoadingDialog
import org.cornfarmer.persentation.main.MainActivity
import org.cornfarmer.persentation.search.SearchFragment
import org.cornfarmer.persentation.wishlist.WishlistActivity

class HomeFragment : Fragment(), HomeFragmentView {
    private lateinit var binding: FragmentHomeBinding

    var list: List<MovieDto> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loadingAnimDialog = CustomLoadingDialog(requireContext())
        loadingAnimDialog.setCancelable(false)
        loadingAnimDialog.setCanceledOnTouchOutside(false)
        loadingAnimDialog.show()
        Handler().postDelayed({
            loadingAnimDialog.dismiss()
        }, 1500)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        val serverToken = sharedPreferences?.getString("servertoken", "")
        Log.d("토큰", serverToken.toString())

        val mActivity = activity as MainActivity // 메인 액티비티

        binding.mainSearchIv.setOnClickListener {
            mActivity.callFragment(SearchFragment())
        }

        if (serverToken == "") { // 비로그인
            val service = HomeService(this, "")
            service.tryGetMovieList()
        } else {
            val service = HomeService(this, serverToken)
            service.tryGetMovieList()
        }

        binding.mainLikeIv.setOnClickListener {
            startActivity(Intent(requireContext(), WishlistActivity::class.java))
        }

        return binding.root
    }

    override fun onGetMovieListSuccess(response: MovieResponse) {
        list = response.result

        Log.d("HomeFragment", "$list")

        binding.mainLikeIv

        val todayMovieRVAdapter = TodayMovieRVAdapter(list)
        binding.homeOttItemRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.homeOttItemRecyclerview.adapter = todayMovieRVAdapter
        todayMovieRVAdapter.setMyItemClickListener(object :
                TodayMovieRVAdapter.MyItemClickListener {
                override fun onItemClick(MovieDto: MovieDto, position: Int) {
                    val mActivity = activity as MainActivity
                    mActivity.callFragment(DetailFragment(MovieDto.movieIdx!!, -1, ""))
                }
            })
    }

    override fun onGetMovieListFailure(message: String) {
        Log.d("Fail", "작품 정보 가져오기 실패")
    }
}
