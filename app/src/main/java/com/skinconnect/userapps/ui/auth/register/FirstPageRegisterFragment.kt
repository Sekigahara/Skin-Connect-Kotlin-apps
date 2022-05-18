package com.skinconnect.userapps.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.skinconnect.userapps.R
import com.skinconnect.userapps.databinding.FragmentSignUpBinding
import com.skinconnect.userapps.ui.helper.BaseFragment

class FirstPageRegisterFragment : BaseFragment() {
    private lateinit var loginTextView: TextView
    private lateinit var nextButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupView()
        setupAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun setupView() {
        val binding = binding as FragmentSignUpBinding
        loginTextView = binding.login
        nextButton = binding.btnNext
    }

    override fun setupViewModel() {}

    override fun setupAction() {
        val navigationToLogin =
            Navigation.createNavigateOnClickListener(R.id.action_fragmentRegisterFirstPage_to_loginFragment)

        val navigationToNextRegisterPage =
            Navigation.createNavigateOnClickListener(R.id.action_fragmentRegisterFirstPage_to_fragmentRegisterSecondPage)

        loginTextView.setOnClickListener(navigationToLogin)
        nextButton.setOnClickListener(navigationToNextRegisterPage)
    }
}