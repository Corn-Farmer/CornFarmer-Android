package com.example.corn_farmer.src.kakao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.join.JoinProfileActivity
import com.example.corn_farmer.src.kakao.model.KakaoResponse
import com.example.cornfarmer_android.databinding.ActivityLoginBinding
import com.google.gson.Gson
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity(), KakaoView {


    private lateinit var binding: ActivityLoginBinding
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val sharedPreferences = getSharedPreferences("kakaotoken", MODE_PRIVATE)
        val kakaotoken = sharedPreferences.getString("kakaotoken", null)
        val kakaoService = KakaoService(this, kakaotoken.toString())
        kakaoService.tryGetUserInfo()

        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->

            if (error != null) {
                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            } else if (tokenInfo != null) {
                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()

            }
        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->

            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT)
                            .show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT)
                            .show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (token != null) {

                val sharedPreferences = getSharedPreferences("kakaotoken", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("kakaotoken", token.accessToken)
                editor.commit()


            }
        }


        binding.loginNoneLoginBt.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        binding.loginKakaoLoginBt.setOnClickListener {
            if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }

        }

        binding.loginNaverLoginBt.setOnClickListener {
            val intent = Intent(this, JoinProfileActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onKakaoLoginSuccess(response: KakaoResponse) {
        Log.d("Kakao", response.toString())

        if(response.result!!.isNew){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }else {
            val intent = Intent(this, JoinProfileActivity()::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }


    }

    override fun onKakaoLoginFailure(message: String) {
        Log.d("Kakao", message)


    }


}