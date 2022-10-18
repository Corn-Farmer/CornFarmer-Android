package org.cornfarmer.data.view

import org.cornfarmer.data.model.response.ResponseNaver

interface NaverView {
    fun onPostNaverSuccess(response: ResponseNaver)
    fun onPostNAverFailure(message: String)
}
