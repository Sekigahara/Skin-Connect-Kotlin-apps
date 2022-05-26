package com.skinconnect.userapps.data.remote

import com.skinconnect.userapps.data.remote.response.LoginResponse
import com.skinconnect.userapps.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest) : LoginResponse

    @POST("users/register")
    suspend fun register(@Body request: RegisterRequest) : RegisterResponse
}