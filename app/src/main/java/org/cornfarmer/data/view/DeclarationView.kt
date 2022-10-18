package org.cornfarmer.data.view

import org.cornfarmer.data.model.response.ResponseDeclaration

interface DeclarationView {
    fun onPostDeclarationSuccess(response: ResponseDeclaration)
    fun onPostDeclarationFailure(message: String)
}
