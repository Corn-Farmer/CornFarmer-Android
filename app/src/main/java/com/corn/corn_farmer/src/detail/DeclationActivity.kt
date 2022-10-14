package com.corn.corn_farmer.src.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.corn.corn_farmer.MainActivity
import com.corn.corn_farmer.config.Application
import com.corn.corn_farmer.src.detail.model.getDeclationUserAPI
import com.corn.corn_farmer.src.detail.model.sendDeclationAPI
import com.corn.corn_farmer.util.ext.showToast
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivityDeclationBinding

class DeclationActivity : AppCompatActivity(), DeclationView {

    private lateinit var binding: ActivityDeclationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_declation)

        binding.declationPreviousBtnIv.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.declationFinishBt.setOnClickListener {
            val sharedPreferences = Application.joinSharedPreferences
            var serverToken = sharedPreferences?.getString("servertoken", "")

            var reviewIdx = intent.getIntExtra("reviewIdx", 0)
            Log.d("reviewIdx", reviewIdx.toString())

            var reportUser = false
            var banUser = false

            if (binding.declationTextEt.text.toString().isEmpty()) {
                showToast("신고 이유를 적어주세요")
                return@setOnClickListener
            }

            if (binding.declationReportUser.isChecked) {
                reportUser = true
            }
            if (binding.declationBlockUser.isChecked) {
                banUser = true
            }

            val report = sendDeclationAPI(
                binding.declationTextEt.text.toString(),
                reportUser,
                banUser
            )

            var service = DeclationService(this, reviewIdx!!, report, serverToken!!)
            service.tryPostDeclation()
        }
    }

    override fun onPostDeclationSuccess(response: getDeclationUserAPI) {
        if (response.code == 3030) {
            showToast("해당 리뷰를 찾을 수 없습니다")
            return
        } else if (response.code == 2000) {
            showToast("입력값을 확인해 주세요")
            return
        }

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onPostDeclationFailure(message: String) {
    }
}
