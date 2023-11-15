package com.example.iqtest.model

import com.google.gson.annotations.SerializedName

data class Answer(
    @SerializedName("_id")   var answerId: String? = null,
    @SerializedName("answer")var answer: String? = null,
    @SerializedName("points")var points: String? = null,
    @SerializedName("question")var questionId: String? = null,
)




