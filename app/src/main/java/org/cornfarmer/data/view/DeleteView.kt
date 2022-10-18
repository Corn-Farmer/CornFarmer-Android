package org.cornfarmer.data.view

import org.cornfarmer.data.model.response.ResponseDelete

interface DeleteView {
    fun onPutDeleteSuccess(response: ResponseDelete)
    fun onPutDeleteFailure(message: String)
}
