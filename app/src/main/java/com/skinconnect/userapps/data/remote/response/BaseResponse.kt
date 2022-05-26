package com.skinconnect.userapps.data.remote.response

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)