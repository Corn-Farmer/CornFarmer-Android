package org.cornfarmer.data.model.response

data class KeywordResponse(
    var isSuccess: Boolean?,
    var code: Int?,
    var message: String?,
    var result: List<KeywordDto>
)

data class KeywordDto(
    var keywordIdx: Int?,
    var keyword: String?
)
