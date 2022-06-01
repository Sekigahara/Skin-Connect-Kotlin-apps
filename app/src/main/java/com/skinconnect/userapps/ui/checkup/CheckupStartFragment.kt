package com.skinconnect.userapps.ui.checkup

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        setupView()
        setupAction()
    }

    override fun setupAction() {
        val navigationToCameraFragment =
            Navigation.createNavigateOnClickListener(R.id.action_checkupStartFragment_to_cameraFragment)

        (binding as FragmentCheckupStartBinding).btnCamera.setOnClickListener(
            navigationToCameraFragment)
    }

    override fun setupView() {
        if (allPermissionGranted()) return

        ActivityCompat.requestPermissions(
            requireActivity(),
            REQUIRED_PERMISSIONS,
            REQUEST_CODE_PERMISSIONS,
        )
    }

    @Deprecated("Deprecated in Java", ReplaceWith("super.onRequestPermissionsResult(requestCode, permissions, grantResults)",
        "com.skinconnect.userapps.ui.helper.BaseFragment"))
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQUEST_CODE_PERMISSIONS && allPermissionGranted()) return
        Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    override fun setupViewModel() {}

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}