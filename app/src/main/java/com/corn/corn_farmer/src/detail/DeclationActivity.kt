package com.corn.corn_farmer.src.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.corn.corn_farmer.src.detail.model.getDeclationUserAPI
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivityDeclationBinding

class DeclationActivity : AppCompatActivity(), DeclationView {

    lateinit var binding: ActivityDeclationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeclationBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.declationFinishBt.setOnClickListener {

            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            var serverToken = sharedPreferences?.getString("servertoken", "")
            var reviewIdx = sharedPreferences?.getInt("reviewIdx", 0)

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

            var service = DeclationService(this, reviewIdx!!, binding.declationTextEt.text.toString(), serverToken!!)
            service.tryPostDeclation()
        }



    }

    override fun onPostDeclationSuccess(response: getDeclationUserAPI) {
    }

    override fun onPostDeclationFailure(message: String) {
    }
}