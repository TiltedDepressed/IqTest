package com.example.iqtest.model

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("_id")     var questionId: String? = null,
    @SerializedName("question")var question: String? = null,
)




