package com.app.ktorcrud.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Priyanka.
 */

@Serializable
data class LoginRequestModel(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)