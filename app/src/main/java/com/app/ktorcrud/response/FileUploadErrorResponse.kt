package com.app.ktorcrud.response

/**
 * Created by Priyanka.
 */
data class FileUploadErrorResponse(val errors: List<Errors>)

data class Errors(
    var formDataFieldName: String? = null,
    var error: Error? = null
)

data class Error(
    var message: String? = null,
    var code: String? = null
)