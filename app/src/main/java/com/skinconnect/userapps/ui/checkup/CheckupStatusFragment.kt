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
import com.skinconnect.userapps.data.entity.ClassifyRequest
import com.skinconnect.userapps.data.entity.response.ClassifyResponse
import com.skinconnect.userapps.data.repository.Result
import com.skinconnect.userapps.databinding.FragmentCheckupStatusBinding
import com.skinconnect.userapps.ui.helper.BaseFragment
import com.skinconnect.userapps.ui.helper.ViewModelFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class CheckupStatusFragment : BaseFragment() {
    private lateinit var noButton: Button
    private lateinit var yesButton: Button
    private lateinit var refreshButton: Button
    private lateinit var backToHomeSafeButton: Button
    private lateinit var findDoctorButton: Button
    private lateinit var backToHomeDangerButton: Button
    private lateinit var textView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var file: File
    private var isClassified = false
    private val filenameFormat = "dd-MMM-yyyy"

    private val timeStamp =
        SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis())

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
        val binding = binding as FragmentCheckupStatusBinding

        // Manipulate image file
        val photoFile = CheckupStatusFragmentArgs.fromBundle(arguments as Bundle).photoFile
        var bitmap = BitmapFactory.decodeFile(photoFile.file.path)
        bitmap = rotateBitmap(bitmap, photoFile.isBackCamera)
        val filename = "$timeStamp.jpg"
        file = File(requireContext().cacheDir, filename)
        file.createNewFile()
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream)
        val bitmapData = byteArrayOutputStream.toByteArray()
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(bitmapData)

        // Bind views
        binding.imageViewPhotoResult.setImageBitmap(bitmap)
        noButton = binding.buttonBackToCamera
        yesButton = binding.buttonUploadPhoto
        textView = binding.textViewScanStatus
        progressBar = binding.progressBarCheckupStatus
        refreshButton = binding.buttonRefresh
        backToHomeSafeButton = binding.buttonBackToHomeSafe
        findDoctorButton = binding.buttonFindDoctor
        backToHomeDangerButton = binding.buttonBackToHomeDanger
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getCheckupInstance(requireContext())
        val viewModel: CheckupViewModel by viewModels { factory }
        this.viewModel = viewModel

        viewModel.uploadToFirebaseResult.observe(requireActivity()) { observeUploadToFirebase(it) }
        viewModel.classifyImageResult.observe(requireActivity()) { observeClassifyImage(it) }
    }

    override fun setupAction() {
        val toCameraFragment =
            Navigation.createNavigateOnClickListener(R.id.action_checkupStatusFragment_to_cameraFragment)

        noButton.setOnClickListener(toCameraFragment)
        yesButton.setOnClickListener { yesButtonClassifyOnClicked() }
        backToHomeSafeButton.setOnClickListener { requireActivity().finish() }
        backToHomeDangerButton.setOnClickListener { requireActivity().finish() }
        findDoctorButton.setOnClickListener { yesButtonUploadToFirebaseOnClicked() }

        refreshButton.setOnClickListener {
            if (isClassified) yesButtonUploadToFirebaseOnClicked()
            else yesButtonClassifyOnClicked()
        }
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

    private fun yesButtonUploadToFirebaseOnClicked() {
        (viewModel as CheckupViewModel).uploadImageToFirebase(file)
    }

    private fun observeUploadToFirebase(result: Result?) {
        if (result == null) return

        when (result) {
            Result.Loading -> {
                backToHomeDangerButton.visibility = View.GONE
                findDoctorButton.visibility = View.GONE
                refreshButton.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                textView.text = resources.getString(R.string.uploading)
            }
            is Result.Error -> {
                progressBar.visibility = View.GONE
                refreshButton.visibility = View.VISIBLE
                val errorPrefix = resources.getString(R.string.something_went_wrong)
                val text = "$errorPrefix. ${result.error}"
                textView.text = text
            }
            is Result.Success<*> -> {
                progressBar.visibility = View.GONE
                Snackbar.make(binding.root, "Success", Snackbar.LENGTH_SHORT).show()
                backToHomeSafeButton.visibility = View.VISIBLE
            }
        }
    }

    private fun yesButtonClassifyOnClicked() {
        file = reduceFileImage(file)
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart = MultipartBody.Part.createFormData("image", file.name, requestFile)
        val request = ClassifyRequest(imageMultipart)
        (viewModel as CheckupViewModel).classifyImage(request)
    }

    private fun observeClassifyImage(result: Result?) {
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
                val text = "$errorPrefix. ${result.error}"
                textView.text = text
            }
            is Result.Success<*> -> {
                progressBar.visibility = View.GONE
                val data = result.data as ClassifyResponse

                if (data.isDanger) {
                    backToHomeDangerButton.visibility = View.VISIBLE
                    findDoctorButton.visibility = View.VISIBLE
                    val diseaseDetectedText = resources.getString(R.string.disease_detected)

                    val diseaseText =
                        "${resources.getString(R.string.disease_name)}: ${data.disease.diseaseName}"

                    val confidenceText =
                        "${resources.getString(R.string.confidence)}: ${data.disease.confidence}%"

                    val findDoctorText = resources.getString(R.string.find_doctor)

                    val text =
                        "$diseaseDetectedText\n\n$diseaseText\n$confidenceText\n\n$findDoctorText"

                    textView.text = text
                } else {
                    backToHomeSafeButton.visibility = View.VISIBLE
                    textView.text = resources.getString(R.string.disease_not_detected)
                }
            }
        }
    }
}