package com.skinconnect.userapps.util

import com.skinconnect.userapps.data.remote.request.LoginRequest
import com.skinconnect.userapps.data.remote.request.RegisterDetailsRequest
import com.skinconnect.userapps.data.remote.request.RegisterRequest
import com.skinconnect.userapps.data.remote.response.BaseResponse

object DataDummy {
    fun generateDummyMessage() = "success"

    fun generateDummyLoginRequest() = LoginRequest("payjoo23@gmail.com", "payjoo456)(*&")

    fun generateDummyLoginResponse() =
        BaseResponse("Login successfull", "2022-05-01T13:42:25.018Z", "success")

    fun generateDummyRegisterRequest() = RegisterRequest("payjoo23",
        "payjoo23@gmail.com",
        "payjoo456)(*&",
        "payjoo456)(*&",
        RegisterDetailsRequest("male", "23", "56"))

    fun generateDummyRegisterResponse() =
        BaseResponse("Account Created successfull", "2022-05-01T13:42:25.018Z", "success")
}