package com.app.ktorcrud.response

/**
 * Created by Priyanka.
 */
sealed class FileUploadResult {
    data class Success(val data: FileUploadResponse) : FileUploadResult()

    data class Error(val message: FileUploadErrorResponse) : FileUploadResult()

    data class Progress(val progress: Int) : FileUploadResult()
}
