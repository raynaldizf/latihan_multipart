package com.example.latihan_multipart.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.latihan_multipart.R
import com.example.latihan_multipart.databinding.ActivityRegisterBinding
import com.example.latihan_multipart.viewmodel.ViewModelMultipart
import java.io.File

class RegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegisterBinding
    private var imageUri: Uri? = Uri.EMPTY
    private var imageFile: File? = null
    lateinit var viewModel : ViewModelMultipart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}