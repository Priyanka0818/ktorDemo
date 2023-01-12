package com.app.ktorcrud.response

/**
 * Created by Priyanka.
 */
sealed class FileUploadResult {
    data class Success(val data: FileUploadResponse) : FileUploadResult()

    data class Error(val message: String, val cause: Exception? = null) : FileUploadResult()

    data class Progress(val progress: Int) : FileUploadResult()
}
