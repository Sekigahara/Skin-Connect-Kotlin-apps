package com.skinconnect.userapps.data.remote.request

data class LoginRequest(val email: String, val password: String)

data class RegisterDetailsRequest(val gender: String, val age: String, val weight: String)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val passwordConfirm: String,
    val details: RegisterDetailsRequest,
)