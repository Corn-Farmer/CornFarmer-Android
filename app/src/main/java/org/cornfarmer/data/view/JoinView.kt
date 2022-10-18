package org.cornfarmer.data.view

import org.cornfarmer.data.model.response.ResponseJoin

interface JoinView {
    fun onPostJoinSuccess(response: ResponseJoin)
    fun onPostJoinFailure(message: String)
}
