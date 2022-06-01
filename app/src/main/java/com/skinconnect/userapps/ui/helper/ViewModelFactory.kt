package com.skinconnect.userapps.ui.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skinconnect.userapps.data.repository.AuthRepository
import com.skinconnect.userapps.data.repository.BaseRepository
import com.skinconnect.userapps.data.repository.CheckupRepository
import com.skinconnect.userapps.di.Injection
import com.skinconnect.userapps.ui.auth.LoginViewModel
import com.skinconnect.userapps.ui.auth.RegisterViewModel
import com.skinconnect.userapps.ui.auth.SplashViewModel
import com.skinconnect.userapps.ui.checkup.CheckupViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java))
            return RegisterViewModel(repository as AuthRepository) as T
        if (modelClass.isAssignableFrom(LoginViewModel::class.java))
            return LoginViewModel(repository as AuthRepository) as T
        if (modelClass.isAssignableFrom(SplashViewModel::class.java))
            return SplashViewModel(repository as AuthRepository) as T
        if (modelClass.isAssignableFrom(CheckupViewModel::class.java))
            return CheckupViewModel(repository as CheckupRepository) as T

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var authInstance: ViewModelFactory? = null

        fun getAuthInstance(context: Context) : ViewModelFactory {
            if (authInstance == null) {
                val repository = Injection.provideAuthInjection(context)
                authInstance = ViewModelFactory(repository)
            }

            return authInstance as ViewModelFactory
        }

        @Volatile
        private var checkupInstance: ViewModelFactory? = null

        fun getCheckupInstance(context: Context): ViewModelFactory {
            if (checkupInstance == null) {
                val repository = Injection.provideCheckupInjection(context)
                checkupInstance = ViewModelFactory(repository)
            }

            return checkupInstance as ViewModelFactory
        }
    }
}