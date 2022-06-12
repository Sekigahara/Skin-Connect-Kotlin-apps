package com.skinconnect.userapps.data.entity

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

open class LoginRequest(private val email: String, private val password: String)

class RegisterRequest(
    private val username: String,
    private val passwordConfirm: String,
    private val details: RegisterDetails,
    email: String,
    password: String,
) : LoginRequest(email, password)

data class ClassifyRequest(
    val image: MultipartBody.Part,
    val description: String = "",
)

data class AddDiseaseRequest(
    val confidence: Float,

    @field:SerializedName("disease_name")
    val diseaseName: String,

    @field:SerializedName("disease_img")
    var diseaseImg: String
)

data class FindDoctorRequest(
    @field:SerializedName("disease_id")
    val diseaseId: String,
)