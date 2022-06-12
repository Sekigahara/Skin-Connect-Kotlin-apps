package com.skinconnect.userapps.ui.checkup

import android.app.Application
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.skinconnect.userapps.R
import com.skinconnect.userapps.data.entity.PhotoFile
import com.skinconnect.userapps.databinding.FragmentCameraBinding
import com.skinconnect.userapps.ui.helper.BaseFragment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraFragment : BaseFragment() {
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private val filenameFormat = "dd-MMM-yyyy"

    private val timeStamp =
        SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    override fun setupAction() {
        val binding = binding as FragmentCameraBinding
        binding.imageViewTakePicture.setOnClickListener { takePhoto(it) }

        binding.imageViewSwitchCamera.setOnClickListener {
            cameraSelector =
                if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                    CameraSelector.DEFAULT_FRONT_CAMERA
                else
                    CameraSelector.DEFAULT_BACK_CAMERA

            startCamera()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    private fun takePhoto(view: View) {
        val imageCapture = imageCapture ?: return
        val file = createFile(requireActivity().application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val photoFile =
                        PhotoFile(file, cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)

                    val toCheckupStatusFragment =
                        CameraFragmentDirections.actionCameraFragmentToCheckupStatusFragment(
                            photoFile)

                    view.findNavController().navigate(toCheckupStatusFragment)
                }

                override fun onError(exception: ImageCaptureException) =
                    Toast.makeText(
                        requireContext(),
                        "Failed to take picture. ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        )
    }

    private fun hideSystemUI() {
        val window = requireActivity().window

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider((binding as FragmentCameraBinding).viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(requireActivity(),
                    cameraSelector,
                    preview,
                    imageCapture)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Failed to show camera", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun createFile(application: Application): File {
        val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
            File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        val outputDirectory =
            if (mediaDir != null && mediaDir.exists()) mediaDir else application.filesDir

        return File(outputDirectory, "$timeStamp.jpg")
    }

    override fun setupView() {}
    override fun setupViewModel() {}
}