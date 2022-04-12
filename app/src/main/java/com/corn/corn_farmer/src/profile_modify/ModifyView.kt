package com.corn.corn_farmer.src.profile_modify

import com.corn.corn_farmer.src.profile.model.ModifyResponse
import com.corn.corn_farmer.src.profile_modify.model.ModifyProfileResponse

interface ModifyView {
    fun onPutModifySuccess(response: ModifyResponse)
    fun onPutModifyFailure(message : String)
    fun onGetModifyProfileSuccess(response: ModifyProfileResponse)
    fun onGetModifyProfileFailure(message : String)
}