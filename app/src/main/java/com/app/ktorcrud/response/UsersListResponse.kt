package com.app.ktorcrud.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * Created by Priyanka.
 */
@Serializable
data class UsersListResponse(
    @SerializedName("page") var page: Int? = 1,
    @SerializedName("per_page") var perPage: Int? = 6,
    @SerializedName("total") var total: Int? = 1,
    @SerializedName("total_pages") var total_pages: Int? = 1,
    @SerializedName("data") var data: ArrayList<Data> = arrayListOf(),
    @SerializedName("support") var support: Support? = Support()
)

@Serializable
data class Data(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("first_name") var first_name: String? = null,
    @SerializedName("last_name") var last_name: String? = null,
    @SerializedName("avatar") var avatar: String? = null
)


@Serializable
data class Support(
    @SerializedName("url") var url: String? = null,
    @SerializedName("text") var text: String? = null
)