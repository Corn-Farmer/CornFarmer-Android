package org.cornfarmer.data.model.response

data class ResponseDeclaration(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: DeclarationResult
)

data class DeclarationResult(
    var reportIdx: Int
)
