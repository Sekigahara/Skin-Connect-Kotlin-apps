package com.skinconnect.userapps.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class RegisterDetails(
    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("age")
    val age: String,

    @field:SerializedName("weight")
    val weight: String
) : Parcelable

@Parcelize
data class PhotoFile(val file: File, val isBackCamera: Boolean) : Parcelable