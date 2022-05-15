package com.skinconnect.userapps.data.remote.retrofit

import com.skinconnect.userapps.data.remote.request.LoginRequest
import com.skinconnect.userapps.data.remote.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest) : BaseResponse
}