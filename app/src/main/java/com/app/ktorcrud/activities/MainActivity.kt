package com.app.ktorcrud.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.app.ktorcrud.BuildConfig
import com.app.ktorcrud.databinding.ActivityMain3Binding
import com.app.ktorcrud.utils.checkPermission
import com.app.ktorcrud.utils.createImageFile
import java.io.File
import java.io.IOException


class MainActivity : BaseActivity() {

    private val binding by lazy { ActivityMain3Binding.inflate(layoutInflater) }
    lateinit var photoUri: Uri
    lateinit var file: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnCamera.setOnClickListener {
            if (checkPermission(android.Manifest.permission.CAMERA)) {
                takePicture()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    1
                )
            }
        }
    }

    private fun takePicture() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            null
        }
        file = photoFile!!
        val photoURI: Uri = FileProvider.getUriForFile(
            this,
            "${BuildConfig.APPLICATION_ID}.provider",
            photoFile
        )
        photoUri = photoURI
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        cameraResultLauncher.launch(takePictureIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture()
            } else {
                if (shouldShowRequestPermissionRationale(permissions[0])) {
                    ActivityCompat.requestPermissions(this, permissions, 1)
                    Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private var cameraResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            Log.e("TAG", ": ")
            binding.imgPhoto.setImageURI(photoUri)
            loginViewModel.uploadImage(file)
        }
    }
}