package com.skinconnect.userapps.data.entity.response

import com.google.gson.annotations.SerializedName

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