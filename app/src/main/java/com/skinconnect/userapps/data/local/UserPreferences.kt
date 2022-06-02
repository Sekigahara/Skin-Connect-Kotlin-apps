package com.skinconnect.userapps.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val userToken = stringPreferencesKey("token_key")
    private val userId = stringPreferencesKey("id_key")

    fun getUserToken() = dataStore.data.map { it[userToken] ?: "" }

    suspend fun saveUserToken(token: String) = dataStore.edit { it[userToken] = token }

    fun getUserId() = dataStore.data.map { it[userId] ?: "" }

    suspend fun saveUserId(id: String) = dataStore.edit { it[userId] = id }

    companion object {
        @Volatile
        private var instance: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>) = instance ?: synchronized(this) {
            instance ?: UserPreferences(dataStore)
        }.also { instance = it }
    }
}