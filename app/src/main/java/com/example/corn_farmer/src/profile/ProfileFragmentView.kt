package com.example.corn_farmer.src.profile

import com.example.corn_farmer.src.profile.model.ProfileResponse

interface ProfileFragmentView {
    fun onGetProfileSuccess(response: ProfileResponse)
    fun onGetProfileFailure(message : String)
}