package com.corn.corn_farmer.src.join

import com.corn.corn_farmer.src.join.model.getJoinAPI

interface JoinView {
    fun onPostJoinSuccess(response: getJoinAPI)
    fun onPostJoinFailure(message: String)
}