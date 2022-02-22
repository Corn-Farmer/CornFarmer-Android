package com.example.corn_farmer.src.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.corn_farmer.MainActivity
import com.example.corn_farmer.src.join.JoinProfileActivity
import com.example.corn_farmer.src.join.TermAgreeActivity
import com.example.corn_farmer.src.kakao.*
import com.example.corn_farmer.src.kakao.model.getKakaoAPI
import com.example.corn_farmer.src.kakao.model.getNaverAPI
import com.example.corn_farmer.src.kakao.model.sendKakaoAPI
import com.example.corn_farmer.src.kakao.model.sendNaverAPI
import com.example.cornfarmer_android.R
import com.kakao.sdk.user.UserApiClient

class SplashActivity : AppCompatActivity(), KakaoView, NaverView {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        UserApiClient.instance.unlink { error -> //카카오 토큰 삭제 -->서버에서 강제 삭제했을 때 앱에서 데이터 지우는 용도
//            if (error != null) {
//                Log.e("카카오토큰 삭제 실패", "연결 끊기 실패", error)
//            } else {
//                Log.i("토큰 삭제 성공", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
//            }
//        }
//        Log.d("회원탈퇴","회원탈퇴 성공")
//        val getSharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
//        val getSharedPreferences2 = getSharedPreferences("userinfo", MODE_PRIVATE)
//        val getSharedPreferences3 = getSharedPreferences("token", MODE_PRIVATE)
//        val editor1 = getSharedPreferences.edit()
//        val editor2 = getSharedPreferences2.edit()
//        val editor3 = getSharedPreferences3.edit()
//        editor1.clear()
//        editor2.clear()
//        editor3.putString("kakaotoken",null)
//
//        editor1.commit()
//        editor2.commit()
//        editor3.commit()
//
//        editor1.putString("servertoken","")
//        editor1.commit()

        Handler().postDelayed({
            val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
            val sharedPreferences2 = getSharedPreferences("join", MODE_PRIVATE)
            val serverToken = sharedPreferences2.getString("servertoken","")
            val kakaotoken = sharedPreferences.getString("kakaotoken", null)
            val navertoken = sharedPreferences.getString("navertoken", null)


            val naver = sendNaverAPI(navertoken.toString())
            val kakao = sendKakaoAPI(kakaotoken.toString())
            Log.d("splashToken",serverToken!!)

            //맨 처음에 카카오랑 연동된 유저인지 아닌지 판단


            if (serverToken == "") {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else if(navertoken == null){ //자동 로그인
                var service = KakaoService(this, kakao)
                service.tryPostToken()
            }else if(kakaotoken == null){
                var service = NaverService(this, naver)
                service.tryPostToken()
            }


        }, 2000)


    }

    override fun onPostTokenSuccess(response: getKakaoAPI) {
        val sharedPreferences2 = getSharedPreferences("join", MODE_PRIVATE)
        val servertoken = sharedPreferences2.getString("servertoken", "")
        val photo = sharedPreferences2.getString("check_camera", null)
        val nickname = sharedPreferences2.getString("nickname", null)
        var sex = sharedPreferences2.getString("isMale",null)
        val birthday = sharedPreferences2.getString("birthday", null)
        val ottList = sharedPreferences2.getString("ottlist", null)
        val genreList = sharedPreferences2.getString("genrelist",null)
        if( photo != "완료"){
            startActivity(Intent(this, TermAgreeActivity::class.java))
        }
        else if( ottList==null){
            startActivity(Intent(this, TermAgreeActivity::class.java))
        }
        else if( genreList==null){
            startActivity(Intent(this, TermAgreeActivity::class.java))
        }
        else if( birthday==null){
            startActivity(Intent(this, TermAgreeActivity::class.java))
        }
        else if(  nickname==null){
            startActivity(Intent(this, TermAgreeActivity::class.java))
        }
        else if(sex==null){
            startActivity(Intent(this, TermAgreeActivity::class.java))
        }
        else if (response.isSuccess == true && !(response.result!!.new_result)) {
            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("servertoken", response.result!!.token)
            editor.putInt("userIdx", response.result!!.userIdx)
            editor.commit() // 나중에 지우기
            startActivity(Intent(this, MainActivity::class.java)) //자동로그인
        }
        else if (response.isSuccess == true && response.result!!.new_result) {
            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("servertoken", response.result!!.token)
            editor.putInt("userIdx", response.result!!.userIdx)
            editor.commit() //새로운 사람->회원가입
            startActivity(Intent(this, TermAgreeActivity::class.java))
        }
    }

    override fun onPostTokenFailure(message: String) {
        Log.d("KAKAO-API", message.toString())
}

    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun onPostNaverSuccess(response: getNaverAPI) {
        val sharedPreferences2 = getSharedPreferences("join", MODE_PRIVATE)
        val servertoken = sharedPreferences2.getString("servertoken", "")
        val photo = sharedPreferences2.getString("check_camera", null)
        val nickname = sharedPreferences2.getString("nickname", null)
        var sex = sharedPreferences2.getString("isMale",null)
        val birthday = sharedPreferences2.getString("birthday", null)
        val ottList = sharedPreferences2.getString("ottlist", null)
        val genreList = sharedPreferences2.getString("genrelist",null)
        if( photo != "완료"){
            startActivity(Intent(this, TermAgreeActivity::class.java))
        }
        else if( ottList==null){
            startActivity(Intent(this, TermAgreeActivity::class.java))
        }
        else if( genreList==null){
            startActivity(Intent(this, TermAgreeActivity::class.java))
        }
        else if( birthday==null){
            startActivity(Intent(this, TermAgreeActivity::class.java))
        }
        else if(  nickname==null){
            startActivity(Intent(this, TermAgreeActivity::class.java))
        }
        else if(sex==null){
            startActivity(Intent(this, TermAgreeActivity::class.java))
        }
        else if (response.isSuccess == true && !(response.result!!.new_result)) {
            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("servertoken", response.result!!.token)
            editor.putInt("userIdx", response.result!!.userIdx)
            editor.commit() // 나중에 지우기
            startActivity(Intent(this, MainActivity::class.java)) //자동로그인
        }
        else if (response.isSuccess == true && response.result!!.new_result) {
            val sharedPreferences = getSharedPreferences("join", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("servertoken", response.result!!.token)
            editor.putInt("userIdx", response.result!!.userIdx)
            editor.commit() //새로운 사람->회원가입
            startActivity(Intent(this, TermAgreeActivity::class.java))
        }
    }

    override fun onPostNAverFailure(message: String) {
        Log.d("NAVER-API", message.toString())
    }
}

