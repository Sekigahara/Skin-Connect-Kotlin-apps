package com.skinconnect.userapps.ui.checkup

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skinconnect.userapps.data.entity.AddDiseaseRequest
import com.skinconnect.userapps.data.entity.ClassifyRequest
import com.skinconnect.userapps.data.entity.FindDoctorRequest
import com.skinconnect.userapps.data.repository.CheckupRepository
import com.skinconnect.userapps.data.repository.Result
import com.skinconnect.userapps.databinding.ActivityCheckupBinding
import com.skinconnect.userapps.ui.helper.BaseActivity
import com.skinconnect.userapps.ui.helper.BaseViewModel
import kotlinx.coroutines.launch
import java.io.File

class CheckupActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityCheckupBinding.inflate(layoutInflater)
        onCreateActivity(savedInstanceState, viewBinding)
    }

    override fun setupView() {
        supportActionBar?.hide()
    }
}

class CheckupViewModel(override val repository: CheckupRepository) : BaseViewModel(repository) {
    private val _classifyImageResult = MutableLiveData<Result>()
    val classifyImageResult: LiveData<Result> = _classifyImageResult

    private val _uploadToFirebaseResult = MutableLiveData<Result>()
    val uploadToFirebaseResult: LiveData<Result> = _uploadToFirebaseResult

    private val _addDiseaseResult = MutableLiveData<Result>()
    val addDiseaseResult: LiveData<Result> = _addDiseaseResult

    private val _findDoctorResult = MutableLiveData<Result>()
    val findDoctorResult: LiveData<Result> = _findDoctorResult

    fun uploadImageToFirebase(file: File) {
        _uploadToFirebaseResult.value = Result.Loading
        repository.uploadImageToFirebase(file, _uploadToFirebaseResult)
    }

    fun classifyImage(request: ClassifyRequest) {
        _classifyImageResult.value = Result.Loading
        viewModelScope.launch { repository.classifyImage(request, _classifyImageResult) }
    }

    fun addDisease(id: String, token: String, request: AddDiseaseRequest) {
        _addDiseaseResult.value = Result.Loading
        viewModelScope.launch { repository.addDisease(id, token, request, _addDiseaseResult) }
    }

    fun findDoctor(userId: String, token: String, request: FindDoctorRequest) {
        _findDoctorResult.value = Result.Loading
        viewModelScope.launch { repository.findDoctor(userId, token, request, _findDoctorResult) }
    }
}