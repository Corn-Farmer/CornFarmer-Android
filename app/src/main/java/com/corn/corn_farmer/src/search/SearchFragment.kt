package com.corn.corn_farmer.src.search

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.corn.corn_farmer.MainActivity
import com.corn.corn_farmer.src.search_result.SearchResultFragment
import com.corn.corn_farmer.src.wishlist.WishlistActivity
import com.corn.cornfarmer_android.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    lateinit var binding : FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)

        val mActivity = activity as MainActivity //메인 액티비티

        binding.searchSearchButtonIv.setOnClickListener {
           var searchMovie : String = binding.searchSearchInputEt.text.toString()
            mActivity.callFragment(SearchResultFragment(searchMovie))
        }
        binding.searchSearchInputEt.setOnEditorActionListener{ v,actionId,event->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var searchMovie : String = binding.searchSearchInputEt.text.toString()
                mActivity.callFragment(SearchResultFragment(searchMovie))
                handled = true
            }
            handled
        }
        binding.searchLikeIv.setOnClickListener {
            startActivity(Intent(requireContext(), WishlistActivity::class.java))
        }


        return binding.root
    }
}