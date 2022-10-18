package com.corn.corn_farmer.src.kakao

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.corn.corn_farmer.MainActivity
import com.corn.corn_farmer.config.Application
import com.corn.corn_farmer.src.join.TermAgreeActivity
import com.corn.corn_farmer.src.kakao.model.getKakaoAPI
import com.corn.corn_farmer.src.kakao.model.getNaverAPI
import com.corn.corn_farmer.src.kakao.model.sendKakaoAPI
import com.corn.corn_farmer.src.kakao.model.sendNaverAPI
import com.corn.corn_farmer.util.ext.showToast
import com.corn.cornfarmer_android.R
import com.corn.cornfarmer_android.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler

class LoginActivity : AppCompatActivity(), KakaoView, NaverView {

    private lateinit var binding: ActivityLoginBinding

    lateinit var mOAuthLoginInstance: OAuthLogin
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val naver_client_id = "NfLkiZ5opr9o5hdqRweQ"
        val naver_client_secret = "nkWklfMmjm"
        val naver_client_name = "cornfarmer"

        mContext = this

        mOAuthLoginInstance = OAuthLogin.getInstance()
        mOAuthLoginInstance.init(mContext, naver_client_id, naver_client_secret, naver_client_name)

        val mOAuthLoginHandler: OAuthLoginHandler = @SuppressLint("HandlerLeak")
        object : OAuthLoginHandler() {
            override fun run(success: Boolean) {
                if (success) {
                    val accessToken: String = mOAuthLoginInstance.getAccessToken(baseContext)
                    Log.d("naver", accessToken)

                    val sharedPreferences = Application.tokenSharedPreferences
                    val editor = sharedPreferences.edit()
                    editor.putString("navertoken", accessToken)
                    editor.commit()

                    val naver = sendNaverAPI(accessToken.toString())
                    var service = NaverService(this@LoginActivity, naver)
                    service.tryPostToken()
                } else {
                    val errorCode: String = mOAuthLoginInstance.getLastErrorCode(mContext).code
                    val errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext)

                    showToast("errorCode:$errorCode, errorDesc:$errorDesc")
                }
            }
        }

        binding.btNaverLogin.setOAuthLoginHandler(mOAuthLoginHandler)

        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.d("accessToken", "토큰 정보 보기 실패")
            } else if (tokenInfo != null) {
                Log.d("accessToken", "토큰 정보 보기 성공")
            }
        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        showToast("접근이 거부 됨(동의 취소)")
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        showToast("유효하지 않은 앱")
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        showToast("인증 수단이 유효하지 않아 인증할 수 없는 상태")
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        showToast("요청 파라미터 오류")
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        showToast("유효하지 않은 scope ID")
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        showToast("설정이 올바르지 않음(android key hash")
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        showToast("서버 내부 에러")
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        showToast("앱이 요청 권한이 없음")
                    }
                    else -> { // Unknown
                        showToast("기타 에러")
                    }
                }
            } else if (token != null) {
                Log.d("kakaotoken", token.accessToken.toString())

                val sharedPreferences = Application.tokenSharedPreferences
                val editor = sharedPreferences.edit()
                editor.putString("kakaotoken", token.accessToken.toString())
                editor.commit()

                val kakao = sendKakaoAPI(token.accessToken.toString())
                var service = KakaoService(this, kakao)
                service.tryPostToken()

                Log.d("kakaologin", "로그인에 성공하였습니다.")
            }
        }

        binding.btGuestLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btKakaoLogin.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }

    override fun onPostTokenSuccess(response: getKakaoAPI) {
        Log.d("KAKAO-API", response.toString())

        if (response.code == 4000) {
            showToast("데이터베이스 연결에 실패하였습니다.")
            return
        }

        if (response.isSuccess == true && response.result!!.new_result) {
            val sharedPreferences = Application.joinSharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("servertoken", response.result!!.token)
            editor.putInt("userIdx", response.result!!.userIdx)
            editor.commit()
            startActivity(Intent(this, TermAgreeActivity::class.java))
        } else if (response.isSuccess == true && !(response.result!!.new_result)) {
            val sharedPreferences = Application.joinSharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("servertoken", response.result!!.token)
            editor.putInt("userIdx", response.result!!.userIdx)
            editor.commit()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onPostTokenFailure(message: String) {
        Log.d("KAKAO-API", message.toString())
    }

    override fun onStart() {
        super.onStart()

        val sharedPreferences = Application.tokenSharedPreferences
        val naverToken = sharedPreferences.getString("navertoken", null)
        val kakaoToken = sharedPreferences.getString("kakaotoken", null)

        val naver = sendNaverAPI(naverToken.toString())
        val service2 = NaverService(this, naver)
        service2.tryPostToken()
        val kakao = sendKakaoAPI(kakaoToken.toString())
        var service = KakaoService(this, kakao)
        service.tryPostToken()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun onPostNaverSuccess(response: getNaverAPI) {
        Log.d("NAVER-API", response.toString())

        if (response.code == 4000) {
            showToast("데이터베이스 연결에 실패하였습니다.")
            return
        }

        if (response.isSuccess == true && response.result!!.new_result) {
            val sharedPreferences = Application.joinSharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("servertoken", response.result!!.token)
            editor.putInt("userIdx", response.result!!.userIdx)
            editor.commit()
            startActivity(Intent(this, TermAgreeActivity::class.java))
        } else if (response.isSuccess == true && !(response.result!!.new_result)) {
            val sharedPreferences = Application.joinSharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("servertoken", response.result!!.token)
            editor.putInt("userIdx", response.result!!.userIdx)
            editor.commit() // 나중에 지우기
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onPostNAverFailure(message: String) {
        Log.d("NAVER-API", message.toString())
    }
}
