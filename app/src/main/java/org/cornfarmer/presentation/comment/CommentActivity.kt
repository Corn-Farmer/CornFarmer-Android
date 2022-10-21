package org.cornfarmer.presentation.comment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import org.cornfarmer.R
import org.cornfarmer.data.model.request.RequestReview
import org.cornfarmer.data.model.response.ResponseReview
import org.cornfarmer.data.repository.CommentService
import org.cornfarmer.data.view.CommentFragmentView
import org.cornfarmer.databinding.ActivityCommentBinding
import org.cornfarmer.presentation.main.MainActivity
import org.cornfarmer.util.ext.showToast

class CommentActivity() :
    AppCompatActivity(),
    CommentFragmentView {

    private lateinit var binding: ActivityCommentBinding

    var movieIdx: Int = 0
    var keywordIdx: Int = 0
    var keyword: String? = ""
    val mActivity = MainActivity()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment)
        movieIdx = intent.getIntExtra("movieIdx", 0)
        keywordIdx = intent.getIntExtra("keywordIdx", 0)
        keyword = intent.getStringExtra("keyword")
        val moviePhoto = intent.getStringExtra("moviePhoto")
        Glide.with(this).load(moviePhoto).into(binding.ivMovie)

        getRate()
        initialize()
    }

    override fun onPostReviewSuccess(response: ResponseReview) {
        if (response.code == 2000) {
            showToast("리뷰는 최소 5글자이상,별점 1점이상으로 작성해주세요!")
        } else if (binding.ivFirstCorn.visibility == View.VISIBLE) {
            Log.d("comment", "$response")
            showToast("리뷰가 저장되었습니다.")
            finish()
        }
    }

    override fun onPostReviewFailure(message: String) {
        showToast("네트워크 연결에 실패했습니다.")
    }

    private fun initialize() {
        binding.ivComplete.setOnClickListener {
            val review = RequestReview(
                movieIdx,
                binding.etContent.text.toString(),
                binding.tvSave.text.toString().toDouble()
            )

            val sharedPreferences =
                getSharedPreferences("join", Context.MODE_PRIVATE)
            val servertoken = sharedPreferences.getString("servertoken", "")
            if (servertoken == "") {
                showToast("로그인이 필요한 서비스입니다.")
            } else {
                val service = CommentService(this, review, servertoken!!)
                service.tryPostReview()
            }
        }

        binding.ivCancel.setOnClickListener {
            finish()
        }
    }

    private fun getRate() {
        binding.ivFirstCorn.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.GONE
            binding.ivThirdCornColor.visibility = View.GONE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.VISIBLE
            binding.ivThirdCorn.visibility = View.VISIBLE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (1.0).toString()
        }

        binding.ivFirstCornColor.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.GONE
            binding.ivSecondCornColor.visibility = View.GONE
            binding.ivThirdCornColor.visibility = View.GONE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.VISIBLE
            binding.ivSecondCorn.visibility = View.VISIBLE
            binding.ivThirdCorn.visibility = View.VISIBLE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (0.0).toString()
        }

        binding.ivSecondCorn.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.GONE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.VISIBLE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (2.0).toString()
        }

        binding.ivSecondCornColor.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.GONE
            binding.ivThirdCornColor.visibility = View.GONE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.VISIBLE
            binding.ivThirdCorn.visibility = View.VISIBLE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (1.0).toString()
        }

        binding.ivThirdCorn.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.VISIBLE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.GONE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (3.0).toString()
        }

        binding.ivThirdCornColor.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.GONE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.VISIBLE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (2.0).toString()
        }

        binding.ivFourthCorn.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.VISIBLE
            binding.ivFourthCornColor.visibility = View.VISIBLE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.GONE
            binding.ivFourthCorn.visibility = View.GONE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (4.0).toString()
        }

        binding.ivFourthCornColor.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.VISIBLE
            binding.ivFourthCornColor.visibility = View.GONE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.GONE
            binding.ivFourthCorn.visibility = View.VISIBLE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (3.0).toString()
        }

        binding.ivFifthCorn.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.VISIBLE
            binding.ivFourthCornColor.visibility = View.VISIBLE
            binding.ivFifthCornColor.visibility = View.VISIBLE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.GONE
            binding.ivFourthCorn.visibility = View.GONE
            binding.ivFifthCorn.visibility = View.GONE

            binding.tvSave.text = (5.0).toString()
        }

        binding.ivFifthCornColor.setOnClickListener {
            binding.ivFirstCornColor.visibility = View.VISIBLE
            binding.ivSecondCornColor.visibility = View.VISIBLE
            binding.ivThirdCornColor.visibility = View.VISIBLE
            binding.ivFourthCornColor.visibility = View.VISIBLE
            binding.ivFifthCornColor.visibility = View.GONE

            binding.ivFirstCorn.visibility = View.GONE
            binding.ivSecondCorn.visibility = View.GONE
            binding.ivThirdCorn.visibility = View.GONE
            binding.ivFourthCorn.visibility = View.GONE
            binding.ivFifthCorn.visibility = View.VISIBLE

            binding.tvSave.text = (4.0).toString()
        }
    }
}
