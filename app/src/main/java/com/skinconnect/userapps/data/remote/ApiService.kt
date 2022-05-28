package com.skinconnect.userapps.data.remote

import com.skinconnect.userapps.data.entity.LoginRequest
import com.skinconnect.userapps.data.entity.RegisterRequest
import com.skinconnect.userapps.data.entity.response.LoginResponse
import com.skinconnect.userapps.data.entity.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest) : LoginResponse

    @POST("users/register")
    suspend fun register(@Body request: RegisterRequest) : RegisterResponse
}