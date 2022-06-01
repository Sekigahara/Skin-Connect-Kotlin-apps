package com.skinconnect.userapps.ui.checkup

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.skinconnect.userapps.R
import com.skinconnect.userapps.data.repository.Result
import com.skinconnect.userapps.databinding.FragmentCheckupStatusBinding
import com.skinconnect.userapps.ui.helper.BaseFragment
import com.skinconnect.userapps.ui.helper.ViewModelFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class CheckupStatusFragment : BaseFragment() {
    private lateinit var noButton: Button
    private lateinit var yesButton: Button
    private lateinit var refreshButton: Button
    private lateinit var backToHomeButton: Button
    private lateinit var findDoctorButton: Button
    private lateinit var textView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var file: File

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentCheckupStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
        setupAction()
    }

    override fun setupView() {
        val photoFile = CheckupStatusFragmentArgs.fromBundle(arguments as Bundle).photoFile
        val binding = binding as FragmentCheckupStatusBinding
        file = photoFile.file
        var bitmap = BitmapFactory.decodeFile(file.path)
        bitmap = rotateBitmap(bitmap, photoFile.isBackCamera)
        binding.imageViewPhotoResult.setImageBitmap(bitmap)
        noButton = binding.buttonBackToCamera
        yesButton = binding.buttonUploadPhoto
        textView = binding.textViewScanStatus
        progressBar = binding.progressBarCheckupStatus
        refreshButton = binding.buttonRefresh
        backToHomeButton = binding.buttonBackToHome
        findDoctorButton = binding.buttonFindDoctor
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getCheckupInstance(requireContext())
        val viewModel: CheckupViewModel by viewModels { factory }
        this.viewModel = viewModel

        viewModel.uploadToFirebaseResult.observe(requireActivity()) { observeUploadToFirebase(it) }
    }

    override fun setupAction() {
        val toCameraFragment =
            Navigation.createNavigateOnClickListener(R.id.action_checkupStatusFragment_to_cameraFragment)

        noButton.setOnClickListener(toCameraFragment)
        yesButton.setOnClickListener { yesButtonOnClicked(it) }
        backToHomeButton.setOnClickListener { requireActivity().finish() }
    }

    private fun rotateBitmap(bitmap: Bitmap, isBackCamera: Boolean = false): Bitmap {
        val matrix = Matrix()

        return if (isBackCamera) {
            matrix.postRotate(90f)
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } else {
            matrix.postRotate(-90f)
            matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
    }

    private fun reduceFileImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int

        do {
            val bitmapStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bitmapStream)
            val bitmapPicByteArray = bitmapStream.toByteArray()
            streamLength = bitmapPicByteArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)

        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }

    private fun yesButtonOnClicked(view: View) {
        val file = reduceFileImage(file)
        (viewModel as CheckupViewModel).uploadImageToFirebase(file, {}, {})
    }

    private fun observeUploadToFirebase(result: Result?) {
        if (result == null) return

        when (result) {
            Result.Loading -> {
                yesButton.visibility = View.GONE
                noButton.visibility = View.GONE
                refreshButton.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                textView.text = resources.getString(R.string.uploading)
            }
            is Result.Error -> {
                progressBar.visibility = View.GONE
                refreshButton.visibility = View.VISIBLE
                val errorPrefix = resources.getString(R.string.something_went_wrong)
                textView.text = "$errorPrefix. ${result.error}"
            }
            is Result.Success<*> -> {
                progressBar.visibility = View.GONE
                Snackbar.make(binding.root, "Success", Snackbar.LENGTH_SHORT).show()
                backToHomeButton.visibility = View.VISIBLE
            }
        }
    }
}