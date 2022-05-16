package com.skinconnect.userapps.data.repository

import androidx.lifecycle.liveData
import com.skinconnect.userapps.data.remote.response.BaseResponse
import com.skinconnect.userapps.data.remote.retrofit.ApiService
import retrofit2.HttpException

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

open class BaseRepository(protected val service: ApiService) {
    protected fun callApi(response: BaseResponse) = liveData {
        emit(Result.Loading)
        var resultError: Result.Error

        try {
            val isSuccess = response.status.lowercase().contains("success")

            if (isSuccess) {
                val resultSuccess = Result.Success(response.message)
                emit(resultSuccess)
            } else {
                resultError = Result.Error(response.message)
                emit(resultError)
            }
        } catch (exception: Exception) {
            when (exception) {
                is HttpException -> {
                    resultError = Result.Error(exception.message())
                    emit(resultError)
                }
                else -> {
                    resultError = exception.message?.let { Result.Error(it) } as Result.Error
                    emit(resultError)
                }
            }
        }
    }
}