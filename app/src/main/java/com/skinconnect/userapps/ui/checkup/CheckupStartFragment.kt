package com.skinconnect.userapps.ui.checkup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.skinconnect.userapps.R
import com.skinconnect.userapps.databinding.FragmentCheckupStartBinding
import com.skinconnect.userapps.ui.helper.BaseFragment

class CheckupStartFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentCheckupStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    override fun setupAction() {
        val navigationToCameraFragment =
            Navigation.createNavigateOnClickListener(R.id.action_checkupStartFragment_to_cameraFragment)

        (binding as FragmentCheckupStartBinding).btnCamera.setOnClickListener(
            navigationToCameraFragment)
    }

    override fun setupView() {}
    override fun setupViewModel() {}
}