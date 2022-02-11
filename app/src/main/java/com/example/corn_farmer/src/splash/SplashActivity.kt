package com.example.corn_farmer.src.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.join.JoinProfileActivity
import com.example.corn_farmer.src.kakao.KakaoService
import com.example.corn_farmer.src.kakao.KakaoView
import com.example.corn_farmer.src.kakao.LoginActivity
import com.example.corn_farmer.src.kakao.model.getKakaoAPI
import com.example.corn_farmer.src.kakao.model.sendKakaoAPI
import com.example.cornfarmer_android.R
import com.kakao.sdk.user.UserApiClient

class SplashActivity : AppCompatActivity(), KakaoView {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        Handler().postDelayed({
            val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
            val token = sharedPreferences.getString("kakaotoken", null)
            val kakao = sendKakaoAPI(token.toString())

            if (token == null) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                var service = KakaoService(this, kakao)
                service.tryPostToken()
            }


        }, 2000)


    }

    override fun onPostTokenSuccess(response: getKakaoAPI) {
        if (response.isSuccess == true && response.result!!.new_result) {
            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("servertoken", response.result!!.token)
            editor.putInt("userIdx", response.result!!.userIdx)
            editor.commit()
            startActivity(Intent(this, JoinProfileActivity::class.java))
        } else if (response.isSuccess == true && !(response.result!!.new_result)) {
            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("servertoken", response.result!!.token)
            editor.putInt("userIdx", response.result!!.userIdx)
            editor.commit() // 나중에 지우기
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onPostTokenFailure(message: String) {
        Log.d("KAKAO-API", message.toString())
}

    override fun onPause() {
        super.onPause()
        finish()
    }
}

