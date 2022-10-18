package org.cornfarmer.data.repository

import org.cornfarmer.data.model.response.ResponseDeclaration
import org.cornfarmer.data.service.DeclarationRetrofitInterface
import org.cornfarmer.data.view.DeclarationView
import org.cornfarmer.di.Application
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeclationService(
    var view: DeclarationView,
    var reviewIdx: Int,
    var sendDeclation: org.cornfarmer.data.model.request.RequestDeclaration,
    var token: String
) {
    fun tryPostDeclation() {
        val retrofitInterface =
            Application.sRetrofit.create(DeclarationRetrofitInterface::class.java)
        retrofitInterface.sendDeclationUser(reviewIdx, sendDeclation, token)
            .enqueue(object : Callback<ResponseDeclaration> {

                override fun onResponse(
                    call: Call<ResponseDeclaration>,
                    response: Response<ResponseDeclaration>
                ) {
                    view.onPostDeclarationSuccess(response.body() as ResponseDeclaration)
                }

                override fun onFailure(call: Call<ResponseDeclaration>, t: Throwable) {
                    view.onPostDeclarationFailure(t.message ?: "통신 오류")
                }
            })
    }
}
