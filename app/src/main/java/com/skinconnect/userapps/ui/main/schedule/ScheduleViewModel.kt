package com.skinconnect.userapps.ui.main.schedule

import android.content.Context
import androidx.lifecycle.*
import com.skinconnect.userapps.data.entity.response.ScheduleResponse
import com.skinconnect.userapps.data.entity.response.UserResponse
import com.skinconnect.userapps.data.local.UserPreferences
import com.skinconnect.userapps.data.remote.ApiService
import com.skinconnect.userapps.data.repository.Result
import com.skinconnect.userapps.data.repository.ScheduleRepository
import com.skinconnect.userapps.di.Injection
import com.skinconnect.userapps.ui.helper.ViewModelFactory
import kotlinx.coroutines.launch

open class ScheduleViewModel(private val repo : ScheduleRepository): ViewModel() {
    private val mutableResult = MutableLiveData<Result>()
    val result : LiveData<Result> = mutableResult

    fun saveUserToken(token: String) = viewModelScope.launch { repo.saveUserToken(token) }

    fun getUserToken() : LiveData<String> {
        return repo.getUserToken().asLiveData()
    }

    fun getSchedule(token : String) = repo.schedule(token)
}