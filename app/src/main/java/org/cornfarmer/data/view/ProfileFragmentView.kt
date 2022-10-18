package org.cornfarmer.data.view

import org.cornfarmer.data.model.response.ResponseProfile

interface ProfileFragmentView {
    fun onGetProfileSuccess(response: ResponseProfile)
    fun onGetProfileFailure(message: String)
}
