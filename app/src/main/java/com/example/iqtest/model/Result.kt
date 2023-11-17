package com.example.iqtest.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("_id")   var resultId: String? = null,
    @SerializedName("user")var userId: String? = null,
    @SerializedName("totalPoints")var points: String? = null,
)




