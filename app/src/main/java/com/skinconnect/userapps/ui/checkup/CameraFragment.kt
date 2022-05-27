package com.skinconnect.userapps.ui.checkup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skinconnect.userapps.databinding.FragmentCameraBinding
import com.skinconnect.userapps.ui.helper.BaseFragment

class CameraFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    override fun setupView() {}

    override fun setupViewModel() {}

    override fun setupAction() {}
}