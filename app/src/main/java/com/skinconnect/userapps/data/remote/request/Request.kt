package com.skinconnect.userapps.data.remote.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class LoginRequest(val email: String, val password: String)

@Parcelize
data class RegisterDetailsRequest(val gender: String, val age: String, val weight: String) :
    Parcelable

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val passwordConfirm: String,
    val details: RegisterDetailsRequest,
)