package com.skinconnect.userapps.util

import com.skinconnect.userapps.data.remote.response.BaseResponse

object DataDummy {
    fun generateDummyMessage() = "success"

    fun generateDummyBaseResponse() = BaseResponse("Login successfull", "2022-05-01T13:42:25.018Z", "success")
}