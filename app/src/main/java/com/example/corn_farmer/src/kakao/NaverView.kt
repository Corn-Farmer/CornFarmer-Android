package com.example.corn_farmer.src.kakao

import com.example.corn_farmer.src.kakao.model.getNaverAPI

interface NaverView {
    fun onPostNaverSuccess(response: getNaverAPI)
    fun onPostNAverFailure(message : String)
}