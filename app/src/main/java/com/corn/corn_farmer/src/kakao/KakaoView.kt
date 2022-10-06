package com.corn.corn_farmer.src.kakao

import com.corn.corn_farmer.src.kakao.model.getKakaoAPI

interface KakaoView {
    fun onPostTokenSuccess(response: getKakaoAPI)
    fun onPostTokenFailure(message : String)
}