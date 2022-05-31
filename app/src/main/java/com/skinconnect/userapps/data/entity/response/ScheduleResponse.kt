package com.skinconnect.userapps.data.entity.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ScheduleResponse(
    @field:SerializedName("schedule")
    val schedule: List<ListScheduleItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

@Parcelize
data class ListScheduleItem(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("time")
    val time: String,

    @field:SerializedName("description")
    val description: String,

    ): Parcelable