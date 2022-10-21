package org.cornfarmer.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.cornfarmer.R
import org.cornfarmer.data.model.response.ResponseDeclaration
import org.cornfarmer.data.repository.DeclationService
import org.cornfarmer.data.view.DeclarationView
import org.cornfarmer.databinding.ActivityDeclarationBinding
import org.cornfarmer.di.Application
import org.cornfarmer.presentation.main.MainActivity
import org.cornfarmer.util.ext.showToast

class DeclarationActivity : AppCompatActivity(), DeclarationView {

    private lateinit var binding: ActivityDeclarationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_declaration)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btFinish.setOnClickListener {
            val sharedPreferences = Application.joinSharedPreferences
            val serverToken = sharedPreferences?.getString("servertoken", "")

            val reviewIdx = intent.getIntExtra("reviewIdx", 0)
            Log.d("reviewIdx", reviewIdx.toString())

            var reportUser = false
            var banUser = false

            if (binding.etContent.text.toString().isEmpty()) {
                showToast("신고 이유를 적어주세요")
                return@setOnClickListener
            }

            if (binding.cbReport.isChecked) {
                reportUser = true
            }
            if (binding.cbBlock.isChecked) {
                banUser = true
            }

            val report = org.cornfarmer.data.model.request.RequestDeclaration(
                binding.etContent.text.toString(),
                reportUser,
                banUser
            )

            val service = DeclationService(this, reviewIdx, report, serverToken!!)
            service.tryPostDeclation()
        }
    }

    override fun onPostDeclarationSuccess(response: ResponseDeclaration) {
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

    override fun onPostDeclarationFailure(message: String) {
    }
}
