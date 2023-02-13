package com.app.ktorcrud.response

import kotlinx.serialization.Serializable

@Serializable
class FileUploadErrorResponse {
    val error: Error? = null
    val success: Boolean? = null
    val status: Int? = null

    @Serializable
    class Error {
        val message: String? = null
    }
}
