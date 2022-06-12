package com.skinconnect.userapps.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.skinconnect.userapps.data.local.UserPreferences
import com.skinconnect.userapps.data.remote.ApiConfig
import com.skinconnect.userapps.data.repository.AuthRepository
import com.skinconnect.userapps.data.repository.CheckupRepository
import com.skinconnect.userapps.data.repository.ScheduleRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

object Injection {
    fun provideAuthInjection(context: Context): AuthRepository {
        val service = ApiConfig.getApiService(context)
        val preferences = UserPreferences.getInstance(context.dataStore)
        return AuthRepository.getInstance(service, preferences)
    }

    fun provideCheckupInjection(context: Context): CheckupRepository {
        val service = ApiConfig.getApiService(context)
        val mlService = ApiConfig.getApiService(context, true)
        return CheckupRepository.getInstance(service, mlService)
    }

    fun provideSchedule(context : Context): ScheduleRepository{
        val service = ApiConfig.getApiService(context)
        return ScheduleRepository.getInstance(service)
    }
}