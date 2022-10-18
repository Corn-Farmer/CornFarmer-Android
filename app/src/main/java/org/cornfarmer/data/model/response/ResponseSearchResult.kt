package org.cornfarmer.data.model.response

data class ResponseSearchResult(
    var isSuccess: Boolean?,
    var code: Int?,
    var message: String?,
    var result: List<MovieDto>?
)
