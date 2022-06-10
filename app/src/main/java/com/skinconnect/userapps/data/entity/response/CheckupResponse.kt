package com.skinconnect.userapps.data.entity.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class ClassifyResponse(
    @field:SerializedName("is_danger")
    val isDanger: Boolean,

    @field:SerializedName("disease")
    val disease: DiseaseResponse,

    status: String,
    message: String,
) : BaseResponse(message, status)

data class DiseaseResponse(
    @field:SerializedName("disease_name")
    val diseaseName: String,

    @field:SerializedName("confidence")
    val confidence: Float,
)

class AddDiseaseResponse(
    message: String,
    status: String,

    @field:SerializedName("data")
    val data: DiseaseDetailResponse
) : BaseResponse(message, status)

data class DiseaseDetailResponse(
    @field:SerializedName("_id")
    val diseaseId: String,
)

class FindDoctorResponse(
    status: String,
    message: String,

    @field:SerializedName("data")
    val data: DoctorDataResponse
) : BaseResponse(message, status)

data class DoctorDataResponse(
    @field:SerializedName("doctor_id")
    val doctorId: String,

    @field:SerializedName("details")
    val details: DoctorDetailsResponse
)

@Parcelize
data class DoctorDetailsResponse(
    @field:SerializedName("full_name")
    val fullName: String?,

    @field:SerializedName("address")
    val address: String?,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("weight")
    val weight: Int,

    @field:SerializedName("age")
    val age: Int
) : Parcelable