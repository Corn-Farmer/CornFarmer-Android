package org.cornfarmer.presentation.keyword

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.FragmentKeywordBinding
import org.cornfarmer.data.model.response.KeywordDto
import org.cornfarmer.data.model.response.KeywordResponse
import org.cornfarmer.data.repository.KeywordService
import org.cornfarmer.data.view.KeywordFragmentView
import org.cornfarmer.presentation.keyword.adapter.KeywordRVAdapter
import org.cornfarmer.presentation.main.MainActivity
import org.cornfarmer.presentation.recommend.RecommendFragment
import org.cornfarmer.presentation.search.SearchFragment
import org.cornfarmer.presentation.wishlist.WishlistActivity

class KeywordFragment : Fragment(), KeywordFragmentView {

    private lateinit var binding: FragmentKeywordBinding
    var list: List<KeywordDto> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_keyword, container, false)
        val service = KeywordService(this)
        service.tryGetKeywordList()

        binding.ivRefresh.setOnClickListener {
            service.tryGetKeywordList()
        }

        binding.ivLike.setOnClickListener {
            startActivity(Intent(requireContext(), WishlistActivity::class.java))
        }

        binding.ivSearch.setOnClickListener {
            val mActivity = activity as MainActivity // 메인 액티비티
            mActivity.callFragment(SearchFragment())
        }

        return binding.root
    }

    override fun onGetKeywordListSuccess(response: KeywordResponse) {
        val mActivity = activity as MainActivity // 메인 액티비티
        list = response.result
        Log.d("KeywordFragment", "$list")
        val keywordRVAdapter = KeywordRVAdapter(list)
        binding.rcvKeyword.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rcvKeyword.adapter = keywordRVAdapter
        keywordRVAdapter.setMyItemClickListener(object : KeywordRVAdapter.MyItemClickListener {
            override fun onItemClick(KeyworDto: KeywordDto, positon: Int) {
                mActivity.callFragment(RecommendFragment(response.result[positon].keywordIdx!!))
            }
        })
    }

    override fun onGetKeywordListFailure(message: String) {
        Log.d("KeywordFragment", "실패")
    }
}
