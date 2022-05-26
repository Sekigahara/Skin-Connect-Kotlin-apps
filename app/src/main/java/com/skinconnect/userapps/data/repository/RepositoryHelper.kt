package com.skinconnect.userapps.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.idling.CountingIdlingResource
import com.google.gson.Gson
import com.skinconnect.userapps.BuildConfig
import com.skinconnect.userapps.data.remote.ApiService
import com.skinconnect.userapps.data.remote.response.BaseResponse
import retrofit2.HttpException
import java.net.UnknownHostException

sealed class Result private constructor() {
    data class Success<out T>(val data: T) : Result()
    data class Error(val error: String) : Result()
    object Loading : Result()
}

open class BaseRepository(protected val service: ApiService) {
    private val gson = Gson()

    protected fun processResponse(response: BaseResponse, liveData: MutableLiveData<Result>) {
        val isSuccess = response.status.lowercase().contains("success")
        if (isSuccess) liveData.value = Result.Success(response)
        else liveData.value = Result.Error(response.message)
    }

    protected fun catchError(exception: Exception, liveData: MutableLiveData<Result>) =
        when (exception) {
            is HttpException -> {
                val errorBody = exception.response()?.errorBody()
                val response = gson.fromJson(errorBody?.string(), BaseResponse::class.java)

                if (BuildConfig.DEBUG) {
                    Log.e("HTTPException", response.message)
                }
                liveData.value = Result.Error(response.message)
            }
            is UnknownHostException -> liveData.value =
                Result.Error("Please check your internet connection and try again.")
            else -> liveData.value = exception.message?.let {
                if (BuildConfig.DEBUG) {
                    Log.e("TAGG", "${exception.message}\n")
                    exception.printStackTrace()
                }
                Result.Error(it)
            } as Result.Error
        }
}

object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() = countingIdlingResource.increment()

    fun decrement() {
        if (countingIdlingResource.isIdleNow) return
        countingIdlingResource.decrement()
    }
}

inline fun <T> wrapEspressoIdlingResource(function: () -> T): T {
    EspressoIdlingResource.increment()

    return try {
        function()
    } finally {
        EspressoIdlingResource.decrement()
    }
}