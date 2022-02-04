package com.example.corn_farmer.src.kakao

import com.example.corn_farmer.src.comment.model.getReviewAPI
import com.example.corn_farmer.src.kakao.model.getKakaoAPI

interface KakaoView {
    fun onPostTokenSuccess(response: getKakaoAPI)
    fun onPostTokenFailure(message : String)
}