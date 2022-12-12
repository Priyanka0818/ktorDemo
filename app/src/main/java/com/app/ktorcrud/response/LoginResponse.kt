package com.app.ktorcrud.response

import kotlinx.serialization.Serializable


@Serializable
class LoginResponse {
    var data: Data? = null

    override fun toString(): String {
        return "ClassPojo [data = $data]"
    }

    @Serializable
    class Data {
        var IsAdmin: String? = null
        var Email: String? = null
        var UserId: String? = null
        var AccessToken: String? = null
        var IsFirstTimeLoggedIn: Boolean? = null

        override fun toString(): String {
            return "ClassPojo [IsAdmin = $IsAdmin, Email = $Email, UserId = $UserId, AccessToken = $AccessToken, IsFirstTimeLoggedIn = $IsFirstTimeLoggedIn]"
        }
    }

}