package com.skinconnect.userapps.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.skinconnect.userapps.data.local.UserPreferences
import com.skinconnect.userapps.data.remote.ApiConfig
import com.skinconnect.userapps.data.repository.AuthRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

object Injection {
    fun provideAuthInjection(context: Context): AuthRepository {
        val service = ApiConfig.getApiService(context)
        val preferences = UserPreferences.getInstance(context.dataStore)
        return AuthRepository.getInstance(service, preferences)
    }
}