package org.cornfarmer.data.view

import org.cornfarmer.data.model.response.ModifyProfileResponse
import org.cornfarmer.data.model.response.ModifyResponse

interface ModifyView {
    fun onPutModifySuccess(response: ModifyResponse)
    fun onPutModifyFailure(message: String)
    fun onGetModifyProfileSuccess(response: ModifyProfileResponse)
    fun onGetModifyProfileFailure(message: String)
}
