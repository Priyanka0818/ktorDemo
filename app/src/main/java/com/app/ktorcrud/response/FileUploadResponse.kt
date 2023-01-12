package com.app.ktorcrud.response

import kotlinx.serialization.Serializable

@Serializable
data class FileUploadResponse(
    val files: ArrayList<File>
)

@Serializable
data class File(
    val accountId: String,
    val filePath: String,
    val fileUrl: String,
    val formDataFieldName: String
)