package com.skinconnect.userapps.ui.helper

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.skinconnect.userapps.data.repository.BaseRepository
import com.skinconnect.userapps.data.repository.Result
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

interface BaseView {
    fun setupView()
    fun setupViewModel()
    fun setupAction()
}

abstract class BaseActivity : AppCompatActivity() {
    private var viewBinding: ViewBinding? = null
    protected val binding get() = viewBinding!!

    protected fun onCreateActivity(savedInstanceState: Bundle?, viewBinding: ViewBinding) {
        this.viewBinding = viewBinding
        setContentView(binding.root)
        if (savedInstanceState != null) return
        setupView()
    }

    protected abstract fun setupView()

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}

abstract class BaseFragment : Fragment(), BaseView {
    protected lateinit var viewModel: ViewModel
    protected var viewBinding: ViewBinding? = null
    protected val binding get() = viewBinding!!
    protected var token = ""
    protected var userId = ""

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}

object ViewHelper {
    fun addTextChangeListener(callback: () -> Unit) = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun afterTextChanged(s: Editable?) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
            callback()
    }
}

object FormValidator {
    fun validateAge(age: String) = age.trim().isNotEmpty() && age.trim().length <= 3

    fun validateGender(gender: String) =
        gender.trim().isNotEmpty() && (gender.trim() == "Male" || gender.trim() == "Female")

    fun validateWeight(weight: String) = weight.trim().isNotEmpty() && weight.trim().length <= 3

    fun validateUsername(username: String) =
        username.trim().isNotEmpty() && username.trim().length < 64

    fun validateEmail(email: String) =
        email.trim().isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()

    fun validatePassword(password: String) =
        password.trim().isNotEmpty() && password.trim().length >= 6
}

fun String.withDateFormat(): String {
    val patternSource = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    val date = patternSource.parse(this) as Date
    return DateFormat.getDateInstance(DateFormat.DEFAULT).format(date)
}

open class BaseViewModel(protected open val repository: BaseRepository) : ViewModel() {
    protected val mutableResult = MutableLiveData<Result>()
    val result : LiveData<Result> = mutableResult
}