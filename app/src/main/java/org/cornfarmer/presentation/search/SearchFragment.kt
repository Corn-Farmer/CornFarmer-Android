package org.cornfarmer.presentation.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.cornfarmer.R
import org.cornfarmer.databinding.FragmentSearchBinding
import org.cornfarmer.presentation.main.MainActivity
import org.cornfarmer.presentation.wishlist.WishlistActivity

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        val mActivity = activity as MainActivity // 메인 액티비티

        binding.ivSearchButton.setOnClickListener {
            val searchMovie: String = binding.etSearch.text.toString()
            mActivity.callFragment(SearchResultFragment(searchMovie))
        }
        binding.etSearch.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchMovie: String = binding.etSearch.text.toString()
                mActivity.callFragment(SearchResultFragment(searchMovie))
                handled = true
            }
            handled
        }

        binding.ivLike.setOnClickListener {
            startActivity(Intent(requireContext(), WishlistActivity::class.java))
        }

        return binding.root
    }
}
