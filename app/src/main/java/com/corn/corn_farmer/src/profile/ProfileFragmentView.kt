package com.corn.corn_farmer.src.profile

import com.corn.corn_farmer.src.profile.model.ProfileResponse

interface ProfileFragmentView {
    fun onGetProfileSuccess(response: ProfileResponse)
    fun onGetProfileFailure(message : String)
}