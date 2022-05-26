package com.skinconnect.userapps.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.idling.CountingIdlingResource
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
    protected fun processResponse(response: BaseResponse, liveData: MutableLiveData<Result>) {
        val isSuccess = response.status.lowercase().contains("success")
        if (isSuccess) liveData.value = Result.Success(response)
        else liveData.value = Result.Error(response.message)
    }

    protected fun catchError(exception: Exception, liveData: MutableLiveData<Result>) =
        when (exception) {
            is HttpException -> liveData.value = Result.Error(exception.message())
            is UnknownHostException -> liveData.value =
                Result.Error("Please check your internet connection and try again.")
            else -> liveData.value = exception.message?.let {
                Log.e("TAGG", "${exception.message}\n")
                exception.stackTrace.asList().forEach { item ->
                    Log.e("TAGG", "$item")
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