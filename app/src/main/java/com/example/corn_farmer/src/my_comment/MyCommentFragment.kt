package com.example.corn_farmer.src.my_comment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.corn_farmer.src.my_comment.model.getMyComment
import com.example.cornfarmer_android.databinding.FragmentMyCommentBinding

class MyCommentFragment() : Fragment(), MyCommentFragmentView {
    lateinit var binding : FragmentMyCommentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCommentBinding.inflate(inflater, container, false)

        val sharedPreferences = this.activity?.getSharedPreferences("join", Context.MODE_PRIVATE)
        val userIdx = sharedPreferences?.getInt("userIdx", -1000)

        val service = MyCommentService(this, userIdx!!, "rate")
        service.tryGetMyComment()

        return binding.root
    }

    override fun onGetMyCommentSuccess(response: getMyComment) {
        val result = response.result

        val MyCommentRVAdapter = MyCommentRVAdapter(result)
        binding.mycommentRV.adapter = MyCommentRVAdapter
        binding.mycommentRV.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

    }

    override fun onGetMyCommentFailure(message: String) {
        Toast.makeText(context, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
    }
}