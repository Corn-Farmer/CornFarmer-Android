package com.example.corn_farmer.src.kakao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.comment.CommentService
import com.example.corn_farmer.src.comment.model.sendReviewAPI
import com.example.corn_farmer.src.join.JoinProfileActivity
import com.example.corn_farmer.src.kakao.model.getKakaoAPI
import com.example.corn_farmer.src.kakao.model.sendKakaoAPI
import com.example.cornfarmer_android.R
import com.example.cornfarmer_android.databinding.ActivityLoginBinding
import com.google.gson.Gson
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity(), KakaoView {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 정보 확인
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            }
            else if (tokenInfo != null) {
                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this, JoinProfileActivity::class.java)
//                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                finish()
            }
        }


//        val keyHash = Utility.getKeyHash(this)
//        Log.d("Hash", keyHash)


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
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
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
            }
            else if (token != null) {

                Log.d("LEE", token.accessToken.toString())

                val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("kakaotoken", token.accessToken.toString())
                editor.commit()

                val kakao = sendKakaoAPI(token.accessToken.toString())
                var service = KakaoService(this,kakao)
                service.tryPostToken()

                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this, JoinProfileActivity::class.java)
//                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                finish()
            }
        }

        binding.loginNoneLoginBt.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

       binding.kakaoLoginButton.setOnClickListener {
            if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            }else{
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

        binding.loginNaverLoginBt.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


    }

    override fun onPostTokenSuccess(response: getKakaoAPI) {
        Log.d("KAKAO-API", response.toString())

        if(response.isSuccess == true && response.result!!.new_result){
            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("servertoken", response.result!!.token)
            editor.commit()
            startActivity(Intent(this, JoinProfileActivity::class.java))
        }else if(response.isSuccess == true && !(response.result!!.new_result)){
            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("servertoken", response.result!!.token)
            editor.commit() // 나중에 지우기
            startActivity(Intent(this, JoinProfileActivity::class.java))
            // 나중에 main으로 바꾸기
        }

    }

    override fun onPostTokenFailure(message: String) {
        Log.d("KAKAO-API", message.toString())
    }

    override fun onStart() {
        super.onStart()

        val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
        val kakaoToken = sharedPreferences.getString("kakaotoken", "1")

        val kakao = sendKakaoAPI(kakaoToken.toString())
        var service = KakaoService(this,kakao)
        service.tryPostToken()

    }

}