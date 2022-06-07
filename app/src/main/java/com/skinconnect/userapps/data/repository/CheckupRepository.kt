package com.skinconnect.userapps.data.repository

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
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
) :
    BaseRepository(service) {

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
            val response = BaseResponse("success", "Image successfully uploaded.")
            liveData.value = Result.Success(response)
        }
    }

    suspend fun classifyImage(request: ClassifyRequest, liveData: MutableLiveData<Result>) = try {
        val response = mlService.classify(request.image)
        processResponse(response, liveData)
    } catch (exception: Exception) {
        catchError(exception, liveData)
    }

//    fun includesForUploadFiles() {
//        val storage = Firebase.storage
//
//        // [START upload_create_reference]
//        // Create a storage reference from our app
//        val storageRef = storage.reference
//
//        // Create a reference to "mountains.jpg"
//        val mountainsRef = storageRef.child("mountains.jpg")
//
//        // Create a reference to 'images/mountains.jpg'
//        val mountainImagesRef = storageRef.child("images/mountains.jpg")
//
//        // While the file names are the same, the references point to different files
//        mountainsRef.name == mountainImagesRef.name // true
//        mountainsRef.path == mountainImagesRef.path // false
//        // [END upload_create_reference]
//
//        val imageView = findViewById<ImageView>(R.id.imageView)
//        // [START upload_memory]
//        // Get the data from an ImageView as bytes
//        imageView.isDrawingCacheEnabled = true
//        imageView.buildDrawingCache()
//        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
//        val baos = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//        val data = baos.toByteArray()
//
//        var uploadTask = mountainsRef.putBytes(data)
//        uploadTask.addOnFailureListener {
//            // Handle unsuccessful uploads
//        }.addOnSuccessListener { taskSnapshot ->
//            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
//            // ...
//        }
//        // [END upload_memory]
//
//        // [START upload_stream]
//        val stream = FileInputStream(File("path/to/images/rivers.jpg"))
//
//        uploadTask = mountainsRef.putStream(stream)
//        uploadTask.addOnFailureListener {
//            // Handle unsuccessful uploads
//        }.addOnSuccessListener { taskSnapshot ->
//            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
//            // ...
//        }
//        // [END upload_stream]
//
//        // [START upload_file]
//        var file = Uri.fromFile(File("path/to/images/rivers.jpg"))
//        val riversRef = storageRef.child("images/${file.lastPathSegment}")
//        uploadTask = riversRef.putFile(file)
//
//        // Register observers to listen for when the download is done or if it fails
//        uploadTask.addOnFailureListener {
//            // Handle unsuccessful uploads
//        }.addOnSuccessListener { taskSnapshot ->
//            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
//            // ...
//        }
//        // [END upload_file]
//
//        // [START upload_with_metadata]
//        // Create file metadata including the content type
//        var metadata = storageMetadata {
//            contentType = "image/jpg"
//        }
//
//        // Upload the file and metadata
//        uploadTask = storageRef.child("images/mountains.jpg").putFile(file, metadata)
//        // [END upload_with_metadata]
//
//        // [START manage_uploads]
//        uploadTask = storageRef.child("images/mountains.jpg").putFile(file)
//
//        // Pause the upload
//        uploadTask.pause()
//
//        // Resume the upload
//        uploadTask.resume()
//
//        // Cancel the upload
//        uploadTask.cancel()
//        // [END manage_uploads]
//
//        // [START monitor_upload_progress]
//        // Observe state change events such as progress, pause, and resume
//        // You'll need to import com.google.firebase.storage.ktx.component1 and
//        // com.google.firebase.storage.ktx.component2
//        uploadTask.addOnProgressListener { (bytesTransferred, totalByteCount) ->
//            val progress = (100.0 * bytesTransferred) / totalByteCount
//            Log.d(TAG, "Upload is $progress% done")
//        }.addOnPausedListener {
//            Log.d(TAG, "Upload is paused")
//        }
//        // [END monitor_upload_progress]
//
//        // [START upload_complete_example]
//        // File or Blob
//        file = Uri.fromFile(File("path/to/mountains.jpg"))
//
//        // Create the file metadata
//        metadata = storageMetadata {
//            contentType = "image/jpeg"
//        }
//
//        // Upload file and metadata to the path 'images/mountains.jpg'
//        uploadTask = storageRef.child("images/${file.lastPathSegment}").putFile(file, metadata)
//
//        // Listen for state changes, errors, and completion of the upload.
//        // You'll need to import com.google.firebase.storage.ktx.component1 and
//        // com.google.firebase.storage.ktx.component2
//        uploadTask.addOnProgressListener { (bytesTransferred, totalByteCount) ->
//            val progress = (100.0 * bytesTransferred) / totalByteCount
//            Log.d(TAG, "Upload is $progress% done")
//        }.addOnPausedListener {
//            Log.d(TAG, "Upload is paused")
//        }.addOnFailureListener {
//            // Handle unsuccessful uploads
//        }.addOnSuccessListener {
//            // Handle successful uploads on complete
//            // ...
//        }
//        // [END upload_complete_example]
//
//        // [START upload_get_download_url]
//        val ref = storageRef.child("images/mountains.jpg")
//        uploadTask = ref.putFile(file)
//
//        val urlTask = uploadTask.continueWithTask { task ->
//            if (!task.isSuccessful) {
//                task.exception?.let {
//                    throw it
//                }
//            }
//            ref.downloadUrl
//        }.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val downloadUri = task.result
//            } else {
//                // Handle failures
//                // ...
//            }
//        }
//        // [END upload_get_download_url]
//    }
//
//    fun includesForFileMetadata() {
//        val storage = Firebase.storage
//
//        // [START metadata_get_storage_reference]
//        // Create a storage reference from our app
//        val storageRef = storage.reference
//
//        // Get reference to the file
//        val forestRef = storageRef.child("images/forest.jpg")
//        // [END metadata_get_storage_reference]
//
//        // [START get_file_metadata]
//        forestRef.metadata.addOnSuccessListener { metadata ->
//            // Metadata now contains the metadata for 'images/forest.jpg'
//        }.addOnFailureListener {
//            // Uh-oh, an error occurred!
//        }
//        // [END get_file_metadata]
//
//        // [START update_file_metadata]
//        // Create file metadata including the content type
//        val metadata = storageMetadata {
//            contentType = "image/jpg"
//            setCustomMetadata("myCustomProperty", "myValue")
//        }
//
//        // Update metadata properties
//        forestRef.updateMetadata(metadata).addOnSuccessListener { updatedMetadata ->
//            // Updated metadata is in updatedMetadata
//        }.addOnFailureListener {
//            // Uh-oh, an error occurred!
//        }
//        // [END update_file_metadata]
//    }
//
//    internal inner class MyFailureListener : OnFailureListener {
//        override fun onFailure(exception: Exception) {
//            val errorCode = (exception as StorageException).errorCode
//            val errorMessage = exception.message
//            // test the errorCode and errorMessage, and handle accordingly
//        }
//    }

    companion object {
        @Volatile
        private var instance: CheckupRepository? = null

        fun getInstance(service: ApiService, mlService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: CheckupRepository(service, mlService)
            }.also { instance = it }
    }
}