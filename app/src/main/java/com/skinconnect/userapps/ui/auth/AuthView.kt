package com.skinconnect.userapps.ui.auth

import android.os.Bundle
import androidx.lifecycle.*
import com.skinconnect.userapps.data.entity.LoginRequest
import com.skinconnect.userapps.data.entity.RegisterRequest
import com.skinconnect.userapps.data.repository.AuthRepository
import com.skinconnect.userapps.data.repository.Result
import com.skinconnect.userapps.databinding.ActivityAuthBinding
import com.skinconnect.userapps.ui.helper.BaseActivity
import com.skinconnect.userapps.ui.helper.BaseViewModel
import kotlinx.coroutines.launch

open class AuthViewModel(override val repository: AuthRepository) : BaseViewModel(repository) {
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
        val viewBinding = ActivityAuthBinding.inflate(layoutInflater)
        onCreateActivity(savedInstanceState, viewBinding)
    }

    override fun setupView() {
        supportActionBar?.hide()
    }
}