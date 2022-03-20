package com.corn.corn_farmer.src.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.corn.corn_farmer.MainActivity
import com.corn.corn_farmer.config.Application
import com.corn.corn_farmer.src.comment.model.sendReviewAPI
import com.corn.corn_farmer.src.detail.model.getDeclationUserAPI
import com.corn.corn_farmer.src.detail.model.sendDeclationAPI
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivityDeclationBinding

class DeclationActivity : AppCompatActivity(), DeclationView {

    lateinit var binding: ActivityDeclationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeclationBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

            if(binding.declationTextEt.text.toString().isEmpty()){
                Toast.makeText(this, "신고 이유를 적어주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(binding.declationReportUser.isChecked){
                reportUser = true
            }
            if(binding.declationBlockUser.isChecked){
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

        if(response.code == 3030){
            Toast.makeText(this, "해당 리뷰를 찾을 수 없습니다", Toast.LENGTH_SHORT)
            return
        }else if(response.code == 2000){
            Toast.makeText(this, "입력값을 확인해 주세요", Toast.LENGTH_SHORT).show()
            return
        }

        startActivity(Intent(this, MainActivity::class.java))
        finish()


    }

    override fun onPostDeclationFailure(message: String) {
    }
}