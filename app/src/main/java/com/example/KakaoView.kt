package com.example

interface KakaoView {
    fun onKakaoLoginLoading()
    fun onKakaoLoginSuccess()
    fun onKakaoLoginFailure(code: Int, message: String)
}