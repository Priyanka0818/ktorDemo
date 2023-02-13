package com.app.ktorcrud.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.lifecycle.lifecycleScope
import com.app.ktorcrud.BuildConfig
import com.app.ktorcrud.databinding.ActivityCameraUploadBinding
import com.app.ktorcrud.response.FileUploadResponse
import com.app.ktorcrud.utils.AllEvents
import com.app.ktorcrud.utils.setImage
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraUploadActivity : BaseActivity() {

    private val binding by lazy { ActivityCameraUploadBinding.inflate(layoutInflater) }
    lateinit var currentPhotoPath: String
    var photoFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.loginViewModel = loginViewModel
        binding.btnUpload.setOnClickListener {
            if (checkCameraPermission()) {
                moveToCamera()
            } else {
                askCameraPermission()
            }
        }

        binding.btnDownload.setOnClickListener {
            loginViewModel.downloadImage()
        }
        binding.btnGallery.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        lifecycleScope.launch {
            loginViewModel.allEventsFlow.collect { event ->
                when (event) {
                    is AllEvents.Success<*> -> {
                        val fileUploadResponse = event.data as FileUploadResponse
                        loginViewModel.filePath = fileUploadResponse.data?.media!!
                        binding.img.setImage(fileUploadResponse.data.media!!)
                    }
                    else -> {
                        val asString = event.asString(this@CameraUploadActivity)
                        if (asString !is Unit && asString.toString().isNotBlank()) {
                            Toast.makeText(
                                this@CameraUploadActivity,
                                asString.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

    }

    private val cameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                moveToCamera()
            } else {
                askCameraPermission()
            }
        }

    private fun askCameraPermission() {
        cameraPermission.launch(Manifest.permission.CAMERA)
    }


    private fun moveToCamera() {
        photoFile = try {
            createImageFile()
        } catch (ex: IOException) {
            // Error occurred while creating the File
            null
        }
        val photoURI: Uri = FileProvider.getUriForFile(
            this,
            "${BuildConfig.APPLICATION_ID}.provider",
            photoFile!!
        )
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        cameraResultLauncher.launch(takePictureIntent)
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private var cameraResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            // There are no request codes
            loginViewModel.uploadImage(photoFile!!)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            Log.e("TAG", "absolutePath: $absolutePath")
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
        Log.e("TAG", "createImageFile: $currentPhotoPath")
        return file!!
    }


    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            binding.img.setImage(uri)
            loginViewModel.uploadImage(uri.toFile())
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }
}