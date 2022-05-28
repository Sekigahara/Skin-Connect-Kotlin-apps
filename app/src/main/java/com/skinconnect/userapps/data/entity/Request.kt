package com.skinconnect.userapps.data.entity

open class LoginRequest(private val email: String, private val password: String)

class RegisterRequest(
    private val username: String,
    private val passwordConfirm: String,
    private val details: RegisterDetails,
    email: String,
    password: String,
) : LoginRequest(email, password)