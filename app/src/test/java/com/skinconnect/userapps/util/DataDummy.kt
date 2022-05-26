package com.skinconnect.userapps.util

import com.skinconnect.userapps.data.remote.LoginRequest
import com.skinconnect.userapps.data.remote.RegisterRequest
import com.skinconnect.userapps.data.remote.response.*

object DataDummy {
    fun generateDummyMessage() = "success"

    fun generateDummyLoginRequest() = LoginRequest("payjoo23@gmail.com", "payjoo456)(*&")

    fun generateDummyLoginResponse() =
        LoginResponse("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYyOGRlODYyMzQ3NGJmOWE4ZGU3MmY4ZiIsImlhdCI6MTY1MzQ2NzIzNCwiZXhwIjoxNjYxMjQzMjM0fQ.wed9rgU8_SNGerZeuZvHOt_eBZ4Et35c_jgau1zEdcI",
            "Login successfull",
            "success")

    fun generateDummyRegisterRequest() = RegisterRequest("payjoo23",
        "payjoo23@gmail.com",
        generateDummyRegisterDetails(),
        "payjoo456)(*&",
        "payjoo456)(*&")

    private fun generateDummyRegisterDetails() = RegisterDetails("male", "23", "56")

    fun generateDummyRegisterResponse() = RegisterResponse("success",
        "Account Created successfull",
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYyOGRlODYyMzQ3NGJmOWE4ZGU3MmY4ZiIsImlhdCI6MTY1MzQ2NzIzNCwiZXhwIjoxNjYxMjQzMjM0fQ.wed9rgU8_SNGerZeuZvHOt_eBZ4Et35c_jgau1zEdcI",
        RegisterDataResponse(generateDummyUserResponse()))

    private fun generateDummyUserResponse() = UserResponse(listOf(),
        listOf(),
        generateDummyRegisterDetails(),
        "628de8623474bf9a8de72f8f",
        "bagusgandhi4@gmail.com",
        "bagusgandhi4")
}