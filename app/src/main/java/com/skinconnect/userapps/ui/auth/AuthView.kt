package com.skinconnect.userapps.ui.auth

import android.os.Bundle
import androidx.lifecycle.*
import com.skinconnect.userapps.data.entity.LoginRequest
import com.skinconnect.userapps.data.entity.RegisterRequest
import com.skinconnect.userapps.data.repository.AuthRepository
import com.skinconnect.userapps.data.repository.Result
import com.skinconnect.userapps.data.repository.ScheduleRepository
import com.skinconnect.userapps.databinding.ActivityHostBinding
import com.skinconnect.userapps.ui.helper.BaseActivity
import kotlinx.coroutines.launch

open class AuthViewModel(protected val repository: AuthRepository) : ViewModel() {
    protected val mutableResult = MutableLiveData<Result>()
    val result : LiveData<Result> = mutableResult

    fun saveUserToken(token: String) = viewModelScope.launch { repository.saveUserToken(token) }
}

class LoginViewModel(repository: AuthRepository) : AuthViewModel(repository) {
    fun login(request: LoginRequest) = viewModelScope.launch {
        mutableResult.value = Result.Loading
        repository.login(request, mutableResult)
    }
}

class RegisterViewModel(repository: AuthRepository) : AuthViewModel(repository) {
    fun register(request: RegisterRequest) = viewModelScope.launch {
        mutableResult.value = Result.Loading
        repository.register(request, mutableResult)
    }
}

class SplashViewModel(repository: AuthRepository) : AuthViewModel(repository) {
    fun getUserToken() = repository.getUserToken().asLiveData()
}

class AuthActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityHostBinding.inflate(layoutInflater)
        onCreateActivity(savedInstanceState, viewBinding)
    }

    override fun setupView() { supportActionBar?.hide() }
}