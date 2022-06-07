package com.skinconnect.userapps.data.entity

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

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