package com.skinconnect.userapps.ui.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skinconnect.userapps.data.repository.AuthRepository
import com.skinconnect.userapps.data.repository.BaseRepository
import com.skinconnect.userapps.data.repository.CheckupRepository
import com.skinconnect.userapps.data.repository.ScheduleRepository
import com.skinconnect.userapps.di.Injection
import com.skinconnect.userapps.ui.auth.*
import com.skinconnect.userapps.ui.checkup.CheckupViewModel
import com.skinconnect.userapps.ui.main.schedule.ScheduleViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java))
            return RegisterViewModel(repository as AuthRepository) as T
        if (modelClass.isAssignableFrom(LoginViewModel::class.java))
            return LoginViewModel(repository as AuthRepository) as T
        if (modelClass.isAssignableFrom(AuthViewModel::class.java))
            return AuthViewModel(repository as AuthRepository) as T
        if (modelClass.isAssignableFrom(CheckupViewModel::class.java))
            return CheckupViewModel(repository as CheckupRepository) as T
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java))
            return ScheduleViewModel(repository as ScheduleRepository) as T
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java))
            return ProfileViewModel(repository as AuthRepository) as T
        if (modelClass.isAssignableFrom(DoctorViewModel::class.java))
            return DoctorViewModel(repository as AuthRepository) as T

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

        @Volatile
        private var scheduleInstance: ViewModelFactory? = null

        fun getScheduleInstance(context: Context) : ViewModelFactory =
            scheduleInstance ?: synchronized(this) {
                scheduleInstance ?: ViewModelFactory(Injection.provideSchedule(context))
            }.also { scheduleInstance = it }
    }
}