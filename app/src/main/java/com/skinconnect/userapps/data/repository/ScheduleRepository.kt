package com.skinconnect.userapps.data.repository

import androidx.lifecycle.MutableLiveData
import com.skinconnect.userapps.data.remote.ApiService

class ScheduleRepository(service: ApiService) : BaseRepository(service) {

    suspend fun getSchedule(id: String, token: String, liveData: MutableLiveData<Result>) = try {
        val response = service.schedule(id, "Bearer $token")
        processResponse(response, liveData)
    } catch (exception: Exception) {
        catchError(exception, liveData)
    }

    companion object {
        @Volatile
        private var instance: ScheduleRepository? = null

        fun getInstance(service: ApiService) =
            instance ?: synchronized(this) {
                instance ?: ScheduleRepository(service)
            }.also { instance = it }
    }
}