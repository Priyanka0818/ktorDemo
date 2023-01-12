package com.app.ktorcrud.apicall

import com.app.ktorcrud.request.LoginRequestModel
import com.app.ktorcrud.request.UpdateUserRequest
import com.app.ktorcrud.response.*
import com.google.gson.JsonObject
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import java.io.File
import kotlin.math.roundToInt

/**
 * Created by Priyanka.
 */
class ApiService(private val client: HttpClient) {

    fun uploadImage(file: File): Flow<FileUploadResult> {
        return channelFlow {
            val response: FileUploadResponse = client.post {
                url(ApiRoutes.UPLOAD_IMAGE)
                body = MultiPartFormDataContent(
                    formData {
                        append("file", file.readBytes(), Headers.build {
                            append(HttpHeaders.ContentType, "image/jpg")
                            append(HttpHeaders.ContentDisposition, "filename=" + file.name)
                        })
                    }
                )
                onUpload { bytesSentTotal, contentLength ->
                    val progress = (bytesSentTotal * 100f / contentLength).roundToInt()
                    send(FileUploadResult.Progress(progress))
                }
            }

            try {
                send(FileUploadResult.Success(response))
            } catch (e: Exception) {
                send(FileUploadResult.Error("", e))
            }
        }
    }

    fun downloadImage(file: String): Flow<FileUploadResult> {
        return channelFlow {
            val response: HttpResponse = client.get {
                url(ApiRoutes.DWN_IMAGE)
//                parameter("filePath", "uploads/2023/01/12/JPEG_20230112_163540_4011966056020625355-4nnb.jpg")

                onDownload { bytesSentTotal, contentLength ->
                    val progress = (bytesSentTotal * 100f / contentLength).roundToInt()
                    send(FileUploadResult.Progress(progress))
                }
            }

            try {
                send(FileUploadResult.Success(response as FileUploadResponse))
            } catch (e: Exception) {
                send(FileUploadResult.Error("", e))
            }
        }
    }

    suspend fun getUsers(page: Int): UsersListResponse =
        client.get {
            url(ApiRoutes.USERS)
            parameter("page", page)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

    suspend fun updateUsers(id: Int, updateUserRequest: UpdateUserRequest): UpdateUserResponse =
        client.put {
            url(ApiRoutes.UUSERS)
            parameter("user_id", id)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            body = updateUserRequest
        }

    suspend fun deleteUsers(id: Int): JsonObject =
        client.delete() {
            url(ApiRoutes.DUSERS)
            parameter("user_id", id)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

    suspend fun login(loginRequestModel: LoginRequestModel): LoginResponse =
        client.post {
            url(ApiRoutes.LOGIN)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            body = loginRequestModel
        }
}