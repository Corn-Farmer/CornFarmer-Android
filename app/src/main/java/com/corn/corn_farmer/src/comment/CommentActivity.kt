package com.corn.corn_farmer.src.comment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.corn.corn_farmer.MainActivity
import com.corn.corn_farmer.src.comment.model.getReviewAPI
import com.corn.corn_farmer.src.comment.model.sendReviewAPI
import com.corn.corn_farmer.util.ext.showToast
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivityCommentBinding
import retrofit2.*

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
        var moviePhoto = intent.getStringExtra("moviePhoto")
        Glide.with(this).load(moviePhoto).into(binding.commentMovieImgIv)

        getRate()
        initialize()
    }

    override fun onPostReviewSuccess(response: getReviewAPI) {
        if (response.code == 2000) {
            showToast("리뷰는 최소 5글자이상,별점 1점이상으로 작성해주세요!")
        } else if (binding.commentRateCorn1ColorIv.visibility == View.VISIBLE) {
            Log.d("comment", "$response")
            showToast("리뷰가 저장되었습니다.")
            finish()
        }
    }

    override fun onPostReviewFailure(message: String) {
        showToast("네트워크 연결에 실패했습니다.")
    }

    fun initialize() {
        binding.commentCompleteBtnIv.setOnClickListener {
            val review = sendReviewAPI(
                movieIdx,
                binding.commentEdittext.text.toString(),
                binding.commentRateSaveTv.text.toString().toDouble()
            )

            val sharedPreferences =
                getSharedPreferences("join", Context.MODE_PRIVATE)
            val servertoken = sharedPreferences.getString("servertoken", "")
            if (servertoken == "") {
                showToast("로그인이 필요한 서비스입니다.")
            } else {
                var service = CommentService(this, review, servertoken!!)
                service.tryPostReview()
            }
        }

        binding.commentCancelBtnIv.setOnClickListener {
            finish()
        }
    }

    fun getRate() {
        binding.commentRateCorn1Iv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.GONE
            binding.commentRateCorn3ColorIv.visibility = View.GONE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.VISIBLE
            binding.commentRateCorn3Iv.visibility = View.VISIBLE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (1.0).toString()
        }

        binding.commentRateCorn1ColorIv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.GONE
            binding.commentRateCorn2ColorIv.visibility = View.GONE
            binding.commentRateCorn3ColorIv.visibility = View.GONE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.VISIBLE
            binding.commentRateCorn2Iv.visibility = View.VISIBLE
            binding.commentRateCorn3Iv.visibility = View.VISIBLE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (0.0).toString()
        }

        binding.commentRateCorn2Iv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.GONE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.VISIBLE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (2.0).toString()
        }

        binding.commentRateCorn2ColorIv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.GONE
            binding.commentRateCorn3ColorIv.visibility = View.GONE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.VISIBLE
            binding.commentRateCorn3Iv.visibility = View.VISIBLE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (1.0).toString()
        }

        binding.commentRateCorn3Iv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.GONE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (3.0).toString()
        }

        binding.commentRateCorn3ColorIv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.GONE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.VISIBLE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (2.0).toString()
        }

        binding.commentRateCorn4Iv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn4ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.GONE
            binding.commentRateCorn4Iv.visibility = View.GONE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (4.0).toString()
        }

        binding.commentRateCorn4ColorIv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn4ColorIv.visibility = View.GONE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.GONE
            binding.commentRateCorn4Iv.visibility = View.VISIBLE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (3.0).toString()
        }

        binding.commentRateCorn5Iv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn4ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn5ColorIv.visibility = View.VISIBLE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.GONE
            binding.commentRateCorn4Iv.visibility = View.GONE
            binding.commentRateCorn5Iv.visibility = View.GONE

            binding.commentRateSaveTv.text = (5.0).toString()
        }

        binding.commentRateCorn5ColorIv.setOnClickListener {
            binding.commentRateCorn1ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn2ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn3ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn4ColorIv.visibility = View.VISIBLE
            binding.commentRateCorn5ColorIv.visibility = View.GONE

            binding.commentRateCorn1Iv.visibility = View.GONE
            binding.commentRateCorn2Iv.visibility = View.GONE
            binding.commentRateCorn3Iv.visibility = View.GONE
            binding.commentRateCorn4Iv.visibility = View.GONE
            binding.commentRateCorn5Iv.visibility = View.VISIBLE

            binding.commentRateSaveTv.text = (4.0).toString()
        }
    }
}
