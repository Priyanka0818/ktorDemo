package com.app.ktorcrud.apicall

import com.app.ktorcrud.request.LoginRequestModel
import com.app.ktorcrud.request.UpdateUserRequest
import com.app.ktorcrud.response.LoginResponse
import com.app.ktorcrud.response.UpdateUserResponse
import com.app.ktorcrud.response.UsersListResponse
import com.google.gson.JsonObject
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

/**
 * Created by Priyanka.
 */
class ApiService(private val client: HttpClient) {

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