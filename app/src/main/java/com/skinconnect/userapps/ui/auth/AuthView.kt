package com.skinconnect.userapps.ui.auth

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skinconnect.userapps.data.remote.request.LoginRequest
import com.skinconnect.userapps.data.remote.request.RegisterRequest
import com.skinconnect.userapps.data.repository.AuthRepository
import com.skinconnect.userapps.data.repository.Result
import com.skinconnect.userapps.databinding.ActivityHostBinding
import com.skinconnect.userapps.ui.helper.BaseActivity
import kotlinx.coroutines.launch

open class AuthViewModel(protected val repository: AuthRepository) : ViewModel() {
    protected val mutableResult = MutableLiveData<Result>()
    val result : LiveData<Result> = mutableResult
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

class AuthActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityHostBinding.inflate(layoutInflater)
        onCreateActivity(savedInstanceState, viewBinding)
    }

    override fun setupView() { supportActionBar?.hide() }
}