package com.example.sopt_1.data.response

import com.google.gson.annotations.SerializedName

data class ResponseSignUpData (
    @SerializedName("email")
    val id: String,
    val password: String,
    val sex: String,
    val nickname: String,
    val phone: String,
    val birth: String,
    val data: SignUpData?
)

data class SignUpData(
    val nickname: String
)