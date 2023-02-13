package com.app.ktorcrud.response

import kotlinx.serialization.Serializable

@Serializable
class FileUploadResponse {
    val data: Data? = null
    val success: Boolean? = null
    val status: Int? = null
    val error: Error? = null

    @Serializable
    class Data {
        var id: String? = null
        var url: String? = null
        var media: String? = null
        var thumb: String? = null
        var width: Int? = null
        var height: Int? = null
    }

    @Serializable
    class Error {
        val message: String? = null
    }
}
