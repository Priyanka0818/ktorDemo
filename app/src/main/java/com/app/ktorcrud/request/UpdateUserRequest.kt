package com.app.ktorcrud.request

/**
 * Created by Priyanka.
 */
@kotlinx.serialization.Serializable
data class UpdateUserRequest(val name: String, val job: String)
