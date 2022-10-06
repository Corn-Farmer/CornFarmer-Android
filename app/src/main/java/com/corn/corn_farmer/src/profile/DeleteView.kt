package com.corn.corn_farmer.src.profile

import com.corn.corn_farmer.src.profile.model.DeleteResponse

interface DeleteView {
    fun onPutDeleteSuccess(response: DeleteResponse)
    fun onPutDeleteFailure(message : String)
}