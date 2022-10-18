package org.cornfarmer.data.view

import org.cornfarmer.data.model.response.ResponseKakao

interface KakaoView {
    fun onPostTokenSuccess(response: ResponseKakao)
    fun onPostTokenFailure(message: String)
}
