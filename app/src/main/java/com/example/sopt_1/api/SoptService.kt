package com.example.sopt_1.api

import com.example.sopt_1.data.request.RequestLoginData
import com.example.sopt_1.data.request.RequestSignUpData
import com.example.sopt_1.data.response.ResponseLoginData
import com.example.sopt_1.data.response.ResponseSignUpData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SoptService {
    @POST("/login/signin")
    fun postLogin(
        @Body body: RequestLoginData
    ) : Call<ResponseLoginData>

    @POST("/login/signup")
    fun postSignUp(
        @Body body: RequestSignUpData
    ) : Call<ResponseSignUpData>
}