package com.skinconnect.userapps.ui.helper

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
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
    fun validateEmail(email: String) = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email)
        .matches()

    fun validatePassword(password: String) = password.isNotEmpty() && password.length > 6

    fun validateAge(age: String) = age.isNotEmpty() && age.length <= 3

    fun validateGender(gender: String) =
        gender.isNotEmpty() && (gender.trim() == "Male" || gender.trim() == "Female")

    fun validateWeight(weight: String) = weight.isNotEmpty() && weight.length <= 3
}