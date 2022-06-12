package com.skinconnect.userapps.ui.doctor

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.airbnb.lottie.LottieAnimationView
import com.skinconnect.userapps.R
import com.skinconnect.userapps.data.entity.response.DoctorDetailsResponse
import com.skinconnect.userapps.data.entity.response.DoctorResponse
import com.skinconnect.userapps.data.repository.Result
import com.skinconnect.userapps.databinding.ActivityDoctorBinding
import com.skinconnect.userapps.ui.auth.DoctorViewModel
import com.skinconnect.userapps.ui.helper.BaseActivity
import com.skinconnect.userapps.ui.helper.ViewModelFactory

class DoctorActivity : BaseActivity() {
    private lateinit var viewModel: DoctorViewModel
    private lateinit var progressBar: LottieAnimationView
    private var token = ""
    private var userId = ""
    private var doctorId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityDoctorBinding.inflate(layoutInflater)
        onCreateActivity(savedInstanceState, viewBinding)
        setupAction()
    }

    override fun setupView() {
        supportActionBar?.hide()

        val doctor =
            intent.getParcelableExtra<DoctorDetailsResponse>(EXTRA_DOCTOR)

        progressBar = (binding as ActivityDoctorBinding).doctorProgressBar
        if (doctor != null) bindDoctorData(doctor)
        else setupViewModel()
    }

    private fun bindDoctorData(doctor: DoctorDetailsResponse) {
        val binding = binding as ActivityDoctorBinding
        binding.doctorFullNameTextView.text = doctor.fullName
        binding.doctorGenderTextView.text = doctor.gender
        binding.addressTextView.text = doctor.address
        binding.doctorAgeTextView.text = "${doctor.age}"
        binding.doctorWeightTextView.text = "${doctor.weight}"
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getAuthInstance(this)
        val viewModel: DoctorViewModel by viewModels { factory }
        this.viewModel = viewModel

        viewModel.getUserId().observe(this) {
            userId = it
            if (token.isNotBlank()) viewModel.getDoctor(userId, token)
        }

        viewModel.getUserToken().observe(this) {
            token = it
            if (token.isNotBlank()) viewModel.getDoctor(userId, token)
        }

        viewModel.result.observe(this) { observeGetDoctor(it) }
    }

    private fun observeGetDoctor(result: Result?) {
        if (result == null) return

        when (result) {
            Result.Loading -> progressBar.visibility = View.VISIBLE
            is Result.Error -> {
                progressBar.visibility = View.GONE
                val errorPrefix = resources.getString(R.string.something_went_wrong)
                val text = "$errorPrefix. ${result.error}"
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            }
            is Result.Success<*> -> {
                progressBar.visibility = View.GONE
                val data = (result.data as DoctorResponse).doctor
                doctorId = data.id
                bindDoctorData(data.details)
            }
        }
    }

    private fun setupAction() {
        val binding = binding as ActivityDoctorBinding
        binding.backToHomeButton.setOnClickListener { finish() }
    }

    companion object {
        const val EXTRA_DOCTOR = "extra_doctor"
    }
}
