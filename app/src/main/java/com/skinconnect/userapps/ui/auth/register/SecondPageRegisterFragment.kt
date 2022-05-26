package com.skinconnect.userapps.ui.auth.register

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
import com.skinconnect.userapps.customview.EditText
import com.skinconnect.userapps.customview.EmailEditText
import com.skinconnect.userapps.customview.PasswordEditText
import com.skinconnect.userapps.data.remote.RegisterRequest
import com.skinconnect.userapps.data.remote.response.RegisterDetails
import com.skinconnect.userapps.databinding.FragmentSignUp2Binding
import com.skinconnect.userapps.ui.main.MainActivity
import com.skinconnect.userapps.ui.auth.RegisterViewModel
import com.skinconnect.userapps.ui.helper.BaseFragment
import com.skinconnect.userapps.ui.helper.FormValidator
import com.skinconnect.userapps.ui.helper.ViewHelper
import com.skinconnect.userapps.ui.helper.ViewModelFactory

class SecondPageRegisterFragment : BaseFragment() {
    private lateinit var registerDetailRequest: RegisterDetails
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EmailEditText
    private lateinit var passwordEditText: PasswordEditText
    private lateinit var registerButton: Button
    private lateinit var registerProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentSignUp2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
        setupAction()
    }

    override fun setupView() {
        registerDetailRequest =
            SecondPageRegisterFragmentArgs.fromBundle(arguments as Bundle).request

        val binding = binding as FragmentSignUp2Binding
        usernameEditText = binding.cvUsername
        emailEditText = binding.cvEmail
        passwordEditText = binding.cvPassword
        registerButton = binding.btnCreate
        registerProgressBar = binding.progressBarRegister
        setRegisterButtonEnable()
    }

    override fun setupAction() {
        val binding = binding as FragmentSignUp2Binding

        val navigationToPreviousRegisterPage =
            Navigation.createNavigateOnClickListener(R.id.action_fragmentRegisterSecondPage_to_fragmentRegisterFirstPage)

        binding.buttonBackSecondRegisterPage.setOnClickListener(navigationToPreviousRegisterPage)
        val textWatcher = ViewHelper.addTextChangeListener { setRegisterButtonEnable() }
        usernameEditText.addTextChangedListener(textWatcher)
        emailEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)

        registerButton.setOnClickListener {
            val username = "${usernameEditText.text}".trim()
            val email = "${emailEditText.text}".trim()
            val password = "${passwordEditText.text}".trim()
            val request =
                RegisterRequest(username, password, registerDetailRequest, email, password)

            val viewModel = this.viewModel as RegisterViewModel
            viewModel.register(request)
        }
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getAuthInstance(requireContext())
        val viewModel: RegisterViewModel by viewModels { factory }
        this.viewModel = viewModel

        viewModel.result.observe(requireActivity()) {
            observeResultLiveData(it, registerButton, registerProgressBar) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    private fun setRegisterButtonEnable() {
        val username = "${usernameEditText.text}"
        val email = "${emailEditText.text}"
        val password = "${passwordEditText.text}"

        registerButton.isEnabled =
            FormValidator.validateUsername(username) && FormValidator.validateEmail(email) && FormValidator.validatePassword(
                password)
    }
}