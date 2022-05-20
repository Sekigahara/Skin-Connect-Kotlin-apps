package com.skinconnect.userapps.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.skinconnect.userapps.data.remote.request.LoginRequest
import com.skinconnect.userapps.data.remote.request.RegisterRequest
import com.skinconnect.userapps.data.remote.response.BaseResponse
import com.skinconnect.userapps.data.remote.retrofit.ApiService
import retrofit2.HttpException

class AuthRepository private constructor (service: ApiService) : BaseRepository(service) {

//    fun login(request: LoginRequest)  = liveData {
//        val response = service.login(request)
//        val apiCallLiveData = callApii(response)
//        emitSource(apiCallLiveData)
//    }

    suspend fun register(request: RegisterRequest, liveData: MutableLiveData<Result>) {
        try {
            val response = service.register(request)
            processResponse(response, liveData)
        } catch (exception: Exception) { catchError(exception, liveData) }
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