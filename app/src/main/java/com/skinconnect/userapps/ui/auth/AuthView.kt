package com.skinconnect.userapps.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import com.skinconnect.userapps.R
import com.skinconnect.userapps.data.entity.LoginRequest
import com.skinconnect.userapps.data.entity.RegisterRequest
import com.skinconnect.userapps.data.repository.AuthRepository
import com.skinconnect.userapps.data.repository.Result
import com.skinconnect.userapps.databinding.ActivityAuthBinding
import com.skinconnect.userapps.ui.helper.BaseActivity
import com.skinconnect.userapps.ui.helper.BaseFragment
import com.skinconnect.userapps.ui.helper.BaseViewModel
import kotlinx.coroutines.launch

open class AuthViewModel(override val repository: AuthRepository) : BaseViewModel(repository) {
    fun saveUserToken(token: String) = viewModelScope.launch { repository.saveUserToken(token) }
    fun saveUserId(id: String) = viewModelScope.launch { repository.saveUserId(id) }
    fun getUserId() = repository.getUserId().asLiveData()
    fun getUserToken() = repository.getUserToken().asLiveData()
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
        val viewBinding = ActivityAuthBinding.inflate(layoutInflater)
        onCreateActivity(savedInstanceState, viewBinding)
    }

    override fun setupView() {
        supportActionBar?.hide()
    }
}

abstract class AuthFragment : BaseFragment() {
    private fun showLoading(button: Button, progressBar: ProgressBar) {
        button.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    private fun showError(button: Button, progressBar: ProgressBar, message: String) {
        progressBar.visibility = View.GONE
        button.isEnabled = true
        val errorPrefix = resources.getString(R.string.something_went_wrong)
        Snackbar.make(binding.root, "$errorPrefix. $message", Snackbar.LENGTH_SHORT).show()
    }

    protected fun observeResultLiveData(
        result: Result?,
        button: Button,
        progressBar: ProgressBar,
        callback: () -> Unit,
    ) {
        if (result == null) return

        when (result) {
            is Result.Loading -> showLoading(button, progressBar)
            is Result.Error -> showError(button, progressBar, result.error)
            is Result.Success<*> -> {
                progressBar.visibility = View.GONE
                callback()
            }
        }
    }
}