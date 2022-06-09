package com.skinconnect.userapps.data.remote

import com.skinconnect.userapps.data.entity.AddDiseaseRequest
import com.skinconnect.userapps.data.entity.LoginRequest
import com.skinconnect.userapps.data.entity.RegisterRequest
import com.skinconnect.userapps.data.entity.response.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.*

interface ApiService {
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("users/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @GET("users/schedule")
    suspend fun schedule(@Header("Authorization") authorization: String): ScheduleResponse

    @Multipart
    @POST("classify")
    suspend fun classify(@Part image: MultipartBody.Part): ClassifyResponse

    @POST("users/{id}/diseases")
    suspend fun addDisease(
        @Path("id") id: String,
        @Header("Authorization") authorization: String,
        @Body request: AddDiseaseRequest,
    ): BaseResponse
}