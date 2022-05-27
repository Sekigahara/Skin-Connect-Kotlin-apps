package com.skinconnect.userapps.ui.checkup

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.skinconnect.userapps.R
import com.skinconnect.userapps.databinding.FragmentCheckupStatusBinding
import com.skinconnect.userapps.ui.helper.BaseFragment

class CheckupStatusFragment : BaseFragment() {
    private lateinit var noButton: Button

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
        setupAction()
    }

    override fun setupView() {
        val photoFile = CheckupStatusFragmentArgs.fromBundle(arguments as Bundle).photoFile
        val binding = binding as FragmentCheckupStatusBinding
        val bitmap = BitmapFactory.decodeFile(photoFile.file.path)
        val result = rotateBitmap(bitmap, photoFile.isBackCamera)
        binding.imageViewPhotoResult.setImageBitmap(result)
        noButton = binding.buttonBackToCamera
    }

    override fun setupViewModel() {
    }

    override fun setupAction() {
        val toCameraFragment = Navigation.createNavigateOnClickListener(R.id.action_checkupStatusFragment_to_cameraFragment)
        noButton.setOnClickListener(toCameraFragment)
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
}