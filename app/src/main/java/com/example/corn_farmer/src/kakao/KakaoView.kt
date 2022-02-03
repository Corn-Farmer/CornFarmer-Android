package com.example.corn_farmer.src.kakao

import com.example.corn_farmer.src.kakao.model.KakaoResponse

interface KakaoView {
    fun onKakaoLoginSuccess(response: KakaoResponse)
    fun onKakaoLoginFailure(message: String)
}