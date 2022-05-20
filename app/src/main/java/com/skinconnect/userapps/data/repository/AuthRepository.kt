package com.skinconnect.userapps.data.repository

import androidx.lifecycle.MutableLiveData
import com.skinconnect.userapps.data.remote.request.LoginRequest
import com.skinconnect.userapps.data.remote.request.RegisterRequest
import com.skinconnect.userapps.data.remote.retrofit.ApiService

class AuthRepository private constructor(service: ApiService) : BaseRepository(service) {
    suspend fun login(request: LoginRequest, liveData: MutableLiveData<Result>) =
        wrapEspressoIdlingResource {
            try {
                val response = service.login(request)
                processResponse(response, liveData)
            } catch (exception: Exception) {
                catchError(exception, liveData)
            }
        }

    suspend fun register(request: RegisterRequest, liveData: MutableLiveData<Result>) = try {
        val response = service.register(request)
        processResponse(response, liveData)
    } catch (exception: Exception) {
        catchError(exception, liveData)
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(service: ApiService) =
            instance ?: synchronized(this) { instance ?: AuthRepository(service) }.also {
                instance = it
            }
    }
}