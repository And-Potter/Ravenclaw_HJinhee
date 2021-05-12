package com.example.sopt_1.api

import com.example.sopt_1.data.request.RequestLoginData
import com.example.sopt_1.data.response.ResponseLoginData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SoptService {
    @POST("/login/signin")
    fun postLogin(
        @Body body: RequestLoginData
    ) : Call<ResponseLoginData>
}