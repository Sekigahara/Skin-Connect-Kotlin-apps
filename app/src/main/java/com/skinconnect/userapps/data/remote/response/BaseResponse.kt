package com.skinconnect.userapps.data.remote.response

import com.google.gson.annotations.SerializedName

data class BaseResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("requestAt")
	val requestAt: String,

	@field:SerializedName("status")
	val status: String
)
