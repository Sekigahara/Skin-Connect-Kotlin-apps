package com.skinconnect.userapps.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.skinconnect.userapps.R
import com.skinconnect.userapps.databinding.FragmentLoginBinding
import com.skinconnect.userapps.ui.MainActivity
import com.skinconnect.userapps.ui.helper.BaseFragment

class LoginFragment : BaseFragment() {

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
        setupViewModel()
        setupView()
        setupAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun setupView() {

    }

    override fun setupViewModel() {}

    override fun setupAction() {
        val binding = binding as FragmentLoginBinding
        val navigationToHome = Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_mainActivity23)
        binding.btnLogin.setOnClickListener(navigationToHome)
    }
}