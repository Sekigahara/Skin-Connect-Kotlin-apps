package com.skinconnect.userapps.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.skinconnect.userapps.data.remote.response.RegisterDetails
import kotlinx.parcelize.Parcelize

open class LoginRequest(private val email: String, private val password: String)

class RegisterRequest(
    private val username: String,
    private val passwordConfirm: String,
    private val details: RegisterDetails,
    email: String,
    password: String,
) : LoginRequest(email, password)