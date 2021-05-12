package com.example.sopt_1.data.response

import com.google.gson.annotations.SerializedName

data class ResponseLoginData (
    @SerializedName("email")
    val id: String,
    val password: String,
    val data: LoginData?
)

data class LoginData(
    @SerializedName("UserId")
    val userId: Int,
    val user_nickname: String,
    val token: String
)