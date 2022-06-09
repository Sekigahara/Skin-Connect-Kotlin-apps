package com.skinconnect.userapps.data.repository

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.skinconnect.userapps.data.entity.AddDiseaseRequest
import com.skinconnect.userapps.data.entity.ClassifyRequest
import com.skinconnect.userapps.data.entity.response.BaseResponse
import com.skinconnect.userapps.data.remote.ApiService
import java.io.File
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.*

class CheckupRepository private constructor(
    service: ApiService,
    private val mlService: ApiService,
) : BaseRepository(service) {

    @SuppressLint("SimpleDateFormat")
    fun uploadImageToFirebase(file: File, liveData: MutableLiveData<Result>) {
        val storage = Firebase.storage
        val storageReference = storage.reference
        val parentRef = storageReference.child("images")
        val currentDateTime = Calendar.getInstance().time
        val format = SimpleDateFormat("yyyy-MM-dd-HH_mm_ss")
        val fileName = format.format(currentDateTime)
        val imageRef = parentRef.child("$fileName.jpg")
        val stream = FileInputStream(file)
        val uploadTask = imageRef.putStream(stream)

        uploadTask.addOnFailureListener {
            catchError(it, liveData)
        }.addOnSuccessListener {
            val response = BaseResponse("success", imageRef.path)
            liveData.value = Result.Success(response)
        }
    }

    suspend fun classifyImage(request: ClassifyRequest, liveData: MutableLiveData<Result>) = try {
        val response = mlService.classify(request.image)
        processResponse(response, liveData)
    } catch (exception: Exception) {
        catchError(exception, liveData)
    }

    suspend fun addDisease(
        id: String,
        token: String,
        request: AddDiseaseRequest,
        liveData: MutableLiveData<Result>,
    ) = try {
        val response = service.addDisease(id, "Bearer $token", request)
        processResponse(response, liveData)
    } catch (exception: Exception) {
        catchError(exception, liveData)
    }

    companion object {
        @Volatile
        private var instance: CheckupRepository? = null

        fun getInstance(service: ApiService, mlService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: CheckupRepository(service, mlService)
            }.also { instance = it }
    }
}