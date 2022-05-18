package com.skinconnect.userapps.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.skinconnect.userapps.R
import com.skinconnect.userapps.databinding.FragmentSignUp2Binding
import com.skinconnect.userapps.ui.helper.BaseFragment

class SecondPageRegisterFragment : BaseFragment() {
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
        val binding = binding as FragmentSignUp2Binding
        val navigationToPreviousRegisterPage =
            Navigation.createNavigateOnClickListener(R.id.action_fragmentRegisterSecondPage_to_fragmentRegisterFirstPage)

        binding.buttonBackSecondRegisterPage.setOnClickListener(navigationToPreviousRegisterPage)
    }
}