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

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("username")
	val username: String
)

data class HandledByResponse(
	@field:SerializedName("doctor_id")
	val doctorId: String? = null,
)

class DoctorResponse(
	@field:SerializedName("doctor")
	val doctor: DoctorDataResponse,

	status: String,
	message: String = "Get doctor success"
) : BaseResponse(message, status)

data class DoctorDataResponse(
	@field:SerializedName("details")
	val details: DoctorDetailsResponse,

	@field:SerializedName("_id")
	val id: String
)