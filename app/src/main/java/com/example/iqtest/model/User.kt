package com.example.iqtest.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")var login: String? = null,
    @SerializedName("password")var password: String? = null,
    @SerializedName("role")var role: String? = null,
    @SerializedName("_id")var userId: String? = null,
    @SerializedName("email")var email: String? = null,
    var token: String? = null,
)




