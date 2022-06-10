package com.skinconnect.userapps.data.remote

import com.skinconnect.userapps.data.entity.AddDiseaseRequest
import com.skinconnect.userapps.data.entity.FindDoctorRequest
import com.skinconnect.userapps.data.entity.LoginRequest
import com.skinconnect.userapps.data.entity.RegisterRequest
import com.skinconnect.userapps.data.entity.response.*
import okhttp3.MultipartBody
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
    ): AddDiseaseResponse

    @POST("users/{id}/findDoctor")
    suspend fun findDoctor(
        @Path("id") id: String,
        @Header("Authorization") authorization: String,
        @Body request: FindDoctorRequest,
    ): FindDoctorResponse
}