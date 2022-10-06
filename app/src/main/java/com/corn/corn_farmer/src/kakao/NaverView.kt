package com.corn.corn_farmer.src.kakao

import com.corn.corn_farmer.src.kakao.model.getNaverAPI

interface NaverView {
    fun onPostNaverSuccess(response: getNaverAPI)
    fun onPostNAverFailure(message : String)
}