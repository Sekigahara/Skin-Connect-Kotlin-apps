package com.skinconnect.userapps.di

import android.content.Context
import com.skinconnect.userapps.data.remote.retrofit.ApiConfig
import com.skinconnect.userapps.data.repository.AuthRepository

object Injection {
    fun provideAuthInjection(context: Context): AuthRepository {
        val service = ApiConfig.getApiService(context)
        return AuthRepository.getInstance(service)
    }
}