package com.skinconnect.userapps.ui.doctor

import android.os.Bundle
import com.skinconnect.userapps.data.entity.response.DoctorDetailsResponse
import com.skinconnect.userapps.databinding.ActivityDoctorBinding
import com.skinconnect.userapps.ui.helper.BaseActivity

class DoctorActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityDoctorBinding.inflate(layoutInflater)
        onCreateActivity(savedInstanceState, viewBinding)
        setupAction()
    }

    override fun setupView() {
        supportActionBar?.hide()
        val doctor =
            intent.getParcelableExtra<DoctorDetailsResponse>(EXTRA_DOCTOR) as DoctorDetailsResponse
        val binding = binding as ActivityDoctorBinding
        binding.doctorFullNameTextView.text = doctor.fullName
        binding.doctorGenderTextView.text = doctor.gender
        binding.addressTextView.text = doctor.address
        binding.doctorAgeTextView.text = "${doctor.age}"
        binding.doctorWeightTextView.text = "${doctor.weight}"
    }

    private fun setupAction() {
        val binding = binding as ActivityDoctorBinding
        binding.backToHomeButton.setOnClickListener { finish() }
    }

    companion object {
        const val EXTRA_DOCTOR = "extra_doctor"
    }
}
