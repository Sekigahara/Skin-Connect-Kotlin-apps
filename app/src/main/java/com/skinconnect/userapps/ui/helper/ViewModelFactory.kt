package com.skinconnect.userapps.ui.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skinconnect.userapps.data.repository.AuthRepository
import com.skinconnect.userapps.data.repository.BaseRepository
import com.skinconnect.userapps.di.Injection
import com.skinconnect.userapps.ui.auth.LoginViewModel
import com.skinconnect.userapps.ui.auth.RegisterViewModel
import com.skinconnect.userapps.ui.auth.SplashViewModel

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
    }
}