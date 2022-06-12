package com.skinconnect.userapps.data.entity.response

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

data class ScheduleDataResponse(
    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("items")
    val items: List<ScheduleDataItemResponse>,
)

data class ScheduleDataItemResponse(
    @field:SerializedName("time")
    val time: String,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("done")
    val done: Boolean,

	@field:SerializedName("content")
	val content: String
)