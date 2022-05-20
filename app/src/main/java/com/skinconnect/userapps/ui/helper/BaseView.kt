package com.skinconnect.userapps.ui.helper

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.skinconnect.userapps.data.repository.Result
import com.skinconnect.userapps.databinding.ActivityHostBinding

interface BaseView {
    fun setupView()
    fun setupViewModel()
    fun setupAction()
}

class BaseActivity : AppCompatActivity() {
    private var viewBinding: ViewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHostBinding.inflate(layoutInflater)
        setContentView((viewBinding as ActivityHostBinding).root)
        if (savedInstanceState != null) return
        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}

abstract class BaseFragment : Fragment(), BaseView {
    protected lateinit var viewModel: ViewModel
    protected var viewBinding: ViewBinding? = null
    protected val binding get() = viewBinding!!

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    private fun showLoading(button: Button, progressBar: ProgressBar) {
        button.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    private fun showError(button: Button, progressBar: ProgressBar, message: String) {
        finishLoading(button, progressBar)
        Snackbar.make(binding.root, "Something went wrong. $message", Snackbar.LENGTH_SHORT).show()
    }

    private fun finishLoading(button: Button, progressBar: ProgressBar) {
        button.isEnabled = true
        progressBar.visibility = View.GONE
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
                finishLoading(button, progressBar)
                callback()
            }
        }
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