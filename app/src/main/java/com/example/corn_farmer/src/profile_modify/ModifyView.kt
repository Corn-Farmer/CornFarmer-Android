package com.example.corn_farmer.src.profile_modify

import com.example.corn_farmer.src.profile.model.ModifyResponse

interface ModifyView {
    fun onPutModifySuccess(response: ModifyResponse)
    fun onPutModifyFailure(message : String)
}