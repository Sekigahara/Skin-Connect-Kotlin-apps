package com.skinconnect.userapps.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skinconnect.userapps.data.remote.request.LoginRequest
import com.skinconnect.userapps.data.remote.request.RegisterRequest
import com.skinconnect.userapps.data.repository.AuthRepository
import com.skinconnect.userapps.data.repository.Result
import kotlinx.coroutines.launch

open class AuthViewModel(protected val repository: AuthRepository) : ViewModel()

class LoginViewModel(repository: AuthRepository) : AuthViewModel(repository) {
//    fun login(request: LoginRequest) = repository.login(request)
}

class RegisterViewModel(repository: AuthRepository) : AuthViewModel(repository) {
    private val _result = MutableLiveData<Result>()
    val result : LiveData<Result> = _result

    fun register(request: RegisterRequest) = viewModelScope.launch {
        _result.value = Result.Loading
        repository.register(request, _result)
    }
}