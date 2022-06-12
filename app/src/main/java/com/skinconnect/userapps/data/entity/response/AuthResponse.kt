package com.skinconnect.userapps.data.entity.response

import com.google.gson.annotations.SerializedName
import com.skinconnect.userapps.data.entity.RegisterDetails

// Login
open class LoginResponse(
	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("token")
	val token: String,

	message: String,
	status: String,
) : BaseResponse(message, status)

// Register
class RegisterResponse(
	status: String,
	message: String,

	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("data")
	val data: RegisterDataResponse
) : BaseResponse(message, status)

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

// Profile
class ProfileResponse(
	status: String,
	message: String = "success",

	@field:SerializedName("profile")
	val profile: ProfileDataResponse,
) : BaseResponse(message, status)

data class ProfileDataResponse(
	@field:SerializedName("handled_by")
	val handledBy: HandledByResponse? = null,

	@field:SerializedName("schedule_data")
	val scheduleData: List<ScheduleDataResponse?>? = null,

//	@field:SerializedName("details")
//	val details: ProfileDetailsResponse,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("username")
	val username: String
)

data class ScheduleDataResponse(
	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("items")
	val items: List<ScheduleDataItemResponse?>? = null,
//	@field:SerializedName("createdAt")
//	val createdAt: String? = null,
//
//	@field:SerializedName("doctors")
//	val doctors: String? = null,
//
//	@field:SerializedName("__v")
//	val V: Int? = null,

//	@field:SerializedName("users")
//	val users: String? = null,
//
//	@field:SerializedName("updatedAt")
//	val updatedAt: String? = null
)

data class ScheduleDataItemResponse(
	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("done")
	val done: Boolean? = null,

//	@field:SerializedName("content")
//	val content: String? = null
)

data class HandledByResponse(
	@field:SerializedName("doctor_id")
	val doctorId: String? = null,
)

//data class ProfileDetailsResponse(
//	@field:SerializedName("full_name")
//	val fullName: String? = null,
//
//	@field:SerializedName("gender")
//	val gender: String? = null,
//
//	@field:SerializedName("weight")
//	val weight: Int? = null,
//
//	@field:SerializedName("age")
//	val age: Int? = null
//)