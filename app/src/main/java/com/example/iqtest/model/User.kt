package com.example.iqtest.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")var login: String? = null,
    @SerializedName("password")var password: String? = null,
    @SerializedName("role")var role: String? = null,
    var token: String? = null,
)




