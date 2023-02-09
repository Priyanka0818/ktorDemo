package com.app.ktorcrud.response

data class FileUploadErrorResponse(
    val errors: List<Errors>
)

data class Errors(
    val error: Error,
    val formDataFieldName: String
)


data class Error(
    val code: String,
    val message: String
)