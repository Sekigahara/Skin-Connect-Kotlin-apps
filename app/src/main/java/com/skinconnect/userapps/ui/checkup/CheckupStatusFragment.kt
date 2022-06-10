package com.skinconnect.userapps.ui.checkup

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.skinconnect.userapps.R
import com.skinconnect.userapps.data.entity.AddDiseaseRequest
import com.skinconnect.userapps.data.entity.ClassifyRequest
import com.skinconnect.userapps.data.entity.FindDoctorRequest
import com.skinconnect.userapps.data.entity.response.AddDiseaseResponse
import com.skinconnect.userapps.data.entity.response.BaseResponse
import com.skinconnect.userapps.data.entity.response.ClassifyResponse
import com.skinconnect.userapps.data.entity.response.FindDoctorResponse
import com.skinconnect.userapps.data.repository.Result
import com.skinconnect.userapps.databinding.FragmentCheckupStatusBinding
import com.skinconnect.userapps.ui.auth.AuthViewModel
import com.skinconnect.userapps.ui.doctor.DoctorActivity
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
    private lateinit var authViewModel: AuthViewModel
    private lateinit var addDiseaseRequest: AddDiseaseRequest
    private var isClassified = false
    private var isUploadedToFirebase = false
    private var isDiseaseAdded = false
    private val filenameFormat = "dd-MMM-yyyy"
    private var id = ""
    private var token = ""

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
        yesButton = binding.classifyImageButton
        textView = binding.textViewScanStatus
        progressBar = binding.progressBarCheckupStatus
        refreshButton = binding.buttonRefresh
        backToHomeSafeButton = binding.buttonBackToHomeSafe
        findDoctorButton = binding.buttonFindDoctor
        backToHomeDangerButton = binding.buttonBackToHomeDanger
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

    override fun setupViewModel() {
        val factory = ViewModelFactory.getCheckupInstance(requireContext())
        val viewModel: CheckupViewModel by viewModels { factory }
        this.viewModel = viewModel
        val authFactory = ViewModelFactory.getAuthInstance(requireContext())
        val authViewModel: AuthViewModel by viewModels { authFactory }
        this.authViewModel = authViewModel
        viewModel.classifyImageResult.observe(requireActivity()) { observeClassifyImage(it) }
        viewModel.uploadToFirebaseResult.observe(requireActivity()) { observeUploadToFirebase(it) }
        viewModel.addDiseaseResult.observe(requireActivity()) { observeAddDisease(it) }
        viewModel.findDoctorResult.observe(requireActivity()) { observeFindDoctor(it) }
    }

    private fun observeClassifyImage(result: Result?) {
        if (result == null) return

        when (result) {
            Result.Loading -> {
                yesButton.visibility = View.GONE
                noButton.visibility = View.GONE
                onResultLoading(resources.getString(R.string.uploading))
            }
            is Result.Error -> onResultError(result)
            is Result.Success<*> -> {
                isClassified = true
                val data = result.data as ClassifyResponse

                if (data.isDanger) {
                    backToHomeDangerButton.visibility = View.VISIBLE
                    findDoctorButton.visibility = View.VISIBLE
                    val disease = data.disease
                    addDiseaseRequest =
                        AddDiseaseRequest(disease.confidence, disease.diseaseName, "")
                    val diseaseDetectedText = resources.getString(R.string.disease_detected)
                    val findDoctorText = resources.getString(R.string.find_doctor)

                    val diseaseText =
                        "${resources.getString(R.string.disease_name)}: ${disease.diseaseName}"

                    val confidenceText =
                        "${resources.getString(R.string.confidence)}: ${disease.confidence}%"

                    val text =
                        "$diseaseDetectedText\n\n$diseaseText\n$confidenceText\n\n$findDoctorText"

                    onResultSuccess(text)
                } else {
                    backToHomeSafeButton.visibility = View.VISIBLE
                    onResultSuccess(resources.getString(R.string.disease_not_detected))
                }
            }
        }
    }

    private fun onResultLoading(text: String) {
        refreshButton.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        textView.text = text
    }

    private fun onResultError(result: Result.Error) {
        progressBar.visibility = View.GONE
        refreshButton.visibility = View.VISIBLE
        val errorPrefix = resources.getString(R.string.something_went_wrong)
        val text = "$errorPrefix. ${result.error}"
        textView.text = text
    }

    private fun onResultSuccess(text: String) {
        progressBar.visibility = View.GONE
        textView.text = text
    }

    private fun observeUploadToFirebase(result: Result?) {
        if (result == null) return

        when (result) {
            Result.Loading -> {
                backToHomeDangerButton.visibility = View.GONE
                findDoctorButton.visibility = View.GONE
                onResultLoading(resources.getString(R.string.uploading))
            }
            is Result.Error -> onResultError(result)
            is Result.Success<*> -> {
                isUploadedToFirebase = true
                onResultSuccess(resources.getString(R.string.inputting_disease))
                addDiseaseRequest.diseaseImg = (result.data as BaseResponse).message
                addDisease()
            }
        }
    }

    private fun addDisease() {
        val viewModel = viewModel as CheckupViewModel

        authViewModel.getUserId().observe(requireActivity()) {
            id = it

            if (token.isNotBlank() && token.isNotEmpty())
                viewModel.addDisease(id, token, addDiseaseRequest)
        }

        authViewModel.getUserToken().observe(requireActivity()) {
            token = it

            if (id.isNotBlank() && id.isNotEmpty())
                viewModel.addDisease(id, token, addDiseaseRequest)
        }
    }

    private fun observeAddDisease(result: Result?) {
        if (result == null) return

        when (result) {
            Result.Loading -> onResultLoading(resources.getString(R.string.inputting_disease))
            is Result.Error -> onResultError(result)
            is Result.Success<*> -> {
                isDiseaseAdded = true
                onResultSuccess(resources.getString(R.string.finding_doctor))
                val data = result.data as AddDiseaseResponse
                val request = FindDoctorRequest(data.data.diseaseId)
                (viewModel as CheckupViewModel).findDoctor(id, token, request)
            }
        }
    }

    private fun observeFindDoctor(result: Result?) {
        if (result == null) return

        when (result) {
            Result.Loading -> onResultLoading(resources.getString(R.string.finding_doctor))
            is Result.Error -> onResultError(result)
            is Result.Success<*> -> {
                val data = result.data as FindDoctorResponse
                onResultSuccess(data.message)
                Toast.makeText(requireContext(), "You have found a doctor.", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), DoctorActivity::class.java)
                intent.putExtra(DoctorActivity.EXTRA_DOCTOR, data.data.details)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    override fun setupAction() {
        val toCameraFragment =
            Navigation.createNavigateOnClickListener(R.id.action_checkupStatusFragment_to_cameraFragment)

        noButton.setOnClickListener(toCameraFragment)
        yesButton.setOnClickListener { yesButtonClassifyOnClicked() }
        backToHomeSafeButton.setOnClickListener { requireActivity().finish() }
        backToHomeDangerButton.setOnClickListener { requireActivity().finish() }
        val viewModel = viewModel as CheckupViewModel
        findDoctorButton.setOnClickListener { viewModel.uploadImageToFirebase(file) }

        refreshButton.setOnClickListener {
            if (!isClassified) yesButtonClassifyOnClicked()
            else if (!isUploadedToFirebase) viewModel.uploadImageToFirebase(file)
            else addDisease()
        }
    }

    private fun yesButtonClassifyOnClicked() {
        file = reduceFileImage(file)
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart = MultipartBody.Part.createFormData("image", file.name, requestFile)
        val request = ClassifyRequest(imageMultipart)
        (viewModel as CheckupViewModel).classifyImage(request)
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
}