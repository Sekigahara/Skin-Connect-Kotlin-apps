package com.skinconnect.userapps.ui.main.schedule

import androidx.lifecycle.*
import com.skinconnect.userapps.data.repository.Result
import com.skinconnect.userapps.data.repository.ScheduleRepository
import kotlinx.coroutines.launch

class ScheduleViewModel(private val repo : ScheduleRepository): ViewModel() {
    private val _getScheduleResult = MutableLiveData<Result>()
    val getScheduleResult : LiveData<Result> = _getScheduleResult

    fun getSchedule(id: String, token : String) = viewModelScope.launch {
        _getScheduleResult.value = Result.Loading
        repo.getSchedule(id, token, _getScheduleResult)
    }
}