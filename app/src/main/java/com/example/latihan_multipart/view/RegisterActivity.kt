package com.example.latihan_multipart.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.latihan_multipart.R
import com.example.latihan_multipart.databinding.ActivityRegisterBinding
import com.example.latihan_multipart.viewmodel.ViewModelMultipart
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class RegisterActivity : AppCompatActivity() {
    private val REQUEST_CODE_PERMISSION = 100
    lateinit var binding : ActivityRegisterBinding
    lateinit var image : MultipartBody.Part
    lateinit var viewModel : ViewModelMultipart
    private var imageRes = registerForActivityResult(ActivityResultContracts.GetContent()) { result->
        val contentResolver = this.contentResolver
        val contentType = contentResolver.getType(result!!)
        binding.addImage.setImageURI(result)

        val tempFile = File.createTempFile("image", "jpg",null)
        val inputStream = contentResolver.openInputStream(result)
        tempFile.outputStream().use {
            inputStream?.copyTo(it)
        }
        val reqBody : RequestBody = tempFile.asRequestBody(contentType!!.toMediaType())
        image = MultipartBody.Part.createFormData("image", tempFile.name, reqBody)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.addImage.setOnClickListener{
            postImage()
        }

        binding.postDataCar.setOnClickListener {
            register()
        }
    }

    fun register() {
        val name = binding.inputName.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val email = binding.inputEmail.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val pass = binding.inputPassword.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val phone = binding.inputPhone.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val address = binding.inputAddress.text.toString().toRequestBody("multipart/form-data".toMediaType())
        val city = binding.inputCity.text.toString().toRequestBody("multipart/form-data".toMediaType())

        viewModel.postApi(name, email, pass, phone, address, city, image)
        viewModel.postLiveDataRegisterUser().observe(this){
            if (it != null) {
                Toast.makeText(this,"Register Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"Register Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun postImage() {
        appsPermissions()
    }

    fun galleryAccess() {
        this.intent.type = "image/*"
        imageRes.launch("image/*")
    }

    fun appsPermissions() {
        if (appsIsGranted(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION,)
        ){
            galleryAccess()
        }
    }

    fun appsIsGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                appsIsDenied()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun appsIsDenied() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Permission Denied")
                .setMessage("Please give app permissions.")
            .setPositiveButton(
                "App Setting"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", this.packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }
}