package com.skinconnect.userapps.data.entity.response

import com.google.gson.annotations.SerializedName

class ScheduleResponse(
    @field:SerializedName("schedule")
    val schedule: List<ScheduleDataResponse?>? = null,

    status: String,
    message: String = "Success"
) : BaseResponse(message, status)