package com.example.corn_farmer.src.login

import com.example.corn_farmer.src.login.model.KakaoResponse

interface KakaoView {
    fun onKakaoLoginSuccess(response: KakaoResponse)
    fun onKakaoLoginFailure(message: String)
}