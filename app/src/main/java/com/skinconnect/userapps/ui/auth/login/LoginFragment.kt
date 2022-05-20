package com.skinconnect.userapps.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.skinconnect.userapps.R
import com.skinconnect.userapps.customview.EmailEditText
import com.skinconnect.userapps.customview.PasswordEditText
import com.skinconnect.userapps.data.remote.request.LoginRequest
import com.skinconnect.userapps.databinding.FragmentLoginBinding
import com.skinconnect.userapps.ui.MainActivity
import com.skinconnect.userapps.ui.auth.LoginViewModel
import com.skinconnect.userapps.ui.helper.BaseFragment
import com.skinconnect.userapps.ui.helper.FormValidator
import com.skinconnect.userapps.ui.helper.ViewHelper
import com.skinconnect.userapps.ui.helper.ViewModelFactory

class LoginFragment : BaseFragment() {
    private lateinit var emailEditText: EmailEditText
    private lateinit var passwordEditText: PasswordEditText
    private lateinit var loginButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
        setupAction()
    }

    override fun setupView() {
        val binding = binding as FragmentLoginBinding
        emailEditText = binding.cvEmail
        passwordEditText = binding.cvPassword
        loginButton = binding.btnLogin
        progressBar = binding.progressBarLogin
        setLoginButtonEnable()
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getAuthInstance(requireContext())
        val viewModel: LoginViewModel by viewModels { factory }
        this.viewModel = viewModel

        viewModel.result.observe(requireActivity()) {
            observeResultLiveData(it, loginButton, progressBar) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    override fun setupAction() {
        val binding = binding as FragmentLoginBinding

        val toRegisterPage =
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_fragmentRegisterFirstPage)

        binding.register.setOnClickListener(toRegisterPage)
        val textWatcher = ViewHelper.addTextChangeListener { setLoginButtonEnable() }
        emailEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)

        loginButton.setOnClickListener {
            val email = "${emailEditText.text}"
            val password = "${passwordEditText.text}"
            val request = LoginRequest(email, password)
            (viewModel as LoginViewModel).login(request)
        }
    }

    private fun setLoginButtonEnable() {
        val email = "${emailEditText.text}"
        val password = "${passwordEditText.text}"

        loginButton.isEnabled =
            FormValidator.validateEmail(email) && FormValidator.validatePassword(password)
    }
}