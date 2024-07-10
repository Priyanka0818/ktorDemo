package com.app.ktorcrud.apicall

import com.app.ktorcrud.response.UsersListResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

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
}