package com.skinconnect.userapps.data.entity.response

import com.google.gson.annotations.SerializedName
import com.skinconnect.userapps.data.entity.RegisterDetails

open class LoginResponse(
	@field:SerializedName("token")
	val token: String,

	message: String,

	status: String,
) : BaseResponse(message, status)

class RegisterResponse(
	status: String,
	message: String,
	token: String,

	@field:SerializedName("data")
	val data: RegisterDataResponse
) : LoginResponse(token, message, status)

data class RegisterDataResponse(
	@field:SerializedName("user")
	val data: UserResponse
)

data class UserResponse(
	@field:SerializedName("schedule")
	val schedule: List<Any>,

	@field:SerializedName("disease")
	val disease: List<Any>,

	@field:SerializedName("details")
	val details: RegisterDetails,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)