package com.skinconnect.userapps.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.skinconnect.userapps.data.entity.response.ScheduleResponse
import com.skinconnect.userapps.data.local.UserPreferences
import com.skinconnect.userapps.data.remote.ApiService

class ScheduleRepository(
    service: ApiService,
    private val preferences: UserPreferences,
) : BaseRepository(service) {

    sealed class Result<out R> private constructor() {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val error: String) : Result<Nothing>()
        object Loading : Result<Nothing>()
    }

    fun schedule(token : String): LiveData<Result<ScheduleResponse>> = liveData {
        emit(Result.Loading)
        try {
            val client = service.schedule("Bearer $token")
            emit(Result.Success(client))
        } catch (e : Exception) {
            Log.d("ScheduleRepository", "schedule: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserToken() = preferences.getUserToken()

    suspend fun saveUserToken(token: String) = preferences.saveUserToken(token)

    companion object {
        @Volatile
        private var instance: ScheduleRepository? = null

        fun getInstance(service: ApiService, preferences: UserPreferences) =
            instance ?: synchronized(this) {
                instance ?: ScheduleRepository(service,
                    preferences)
            }.also { instance = it }
    }
}