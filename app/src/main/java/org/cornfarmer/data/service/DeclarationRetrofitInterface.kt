package org.cornfarmer.data.service

import org.cornfarmer.data.model.response.ResponseDeclaration
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface DeclarationRetrofitInterface {
    @POST("/reviews/{reviewIdx}/report")
    fun sendDeclationUser(
        @Path("reviewIdx") reviewIdx: Int,
        @Body sendDeclationAPI: org.cornfarmer.data.model.request.RequestDeclaration,
        @Header("X-ACCESS-TOKEN") token: String
    ): Call<ResponseDeclaration>
}
