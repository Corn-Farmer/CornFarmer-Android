package com.example.corn_farmer.src.profile

import com.example.corn_farmer.src.profile.model.DeleteResponse

interface DeleteView {
    fun onPutDeleteSuccess(response: DeleteResponse)
    fun onPutDeleteFailure(message : String)
}