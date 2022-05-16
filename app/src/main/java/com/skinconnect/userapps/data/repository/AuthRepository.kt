package com.skinconnect.userapps.data.repository

import androidx.lifecycle.liveData
import com.skinconnect.userapps.data.remote.request.LoginRequest
import com.skinconnect.userapps.data.remote.request.RegisterRequest
import com.skinconnect.userapps.data.remote.retrofit.ApiService

class AuthRepository(service: ApiService) : BaseRepository(service) {
    fun login(request: LoginRequest) = liveData {
        val response = service.login(request)
        val apiCallLiveData = callApi(response)
        emitSource(apiCallLiveData)
    }

    fun register(request: RegisterRequest) = liveData {
        val response = service.register(request)
        val apiCallLiveData = callApi(response)
        emitSource(apiCallLiveData)
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