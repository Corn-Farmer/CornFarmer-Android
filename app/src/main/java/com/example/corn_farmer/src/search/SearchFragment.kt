package com.example.corn_farmer.src.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.search_result.SearchResultFragment
import com.example.corn_farmer.src.wishlist.WishlistActivity
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.FragmentSearchBinding

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
        binding.searchLikeIv.setOnClickListener {
            startActivity(Intent(requireContext(), WishlistActivity::class.java))
        }


        return binding.root
    }
}