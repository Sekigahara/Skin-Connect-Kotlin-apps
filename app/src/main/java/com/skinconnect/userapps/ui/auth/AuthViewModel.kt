package com.skinconnect.userapps.ui.auth

import androidx.lifecycle.ViewModel
import com.skinconnect.userapps.data.remote.request.LoginRequest
import com.skinconnect.userapps.data.remote.request.RegisterRequest
import com.skinconnect.userapps.data.repository.AuthRepository

open class AuthViewModel(protected val repository: AuthRepository) : ViewModel()

class LoginViewModel(repository: AuthRepository) : AuthViewModel(repository) {
    fun login(request: LoginRequest) = repository.login(request)
}

class RegisterViewModel(repository: AuthRepository) : AuthViewModel(repository) {
    fun register(request: RegisterRequest) = repository.register(request)
}