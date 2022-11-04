package com.example.latihan_multipart.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.latihan_multipart.model.ResponseRegisterUser
import com.example.latihan_multipart.network.RetrofitClient
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ViewModelMultipart : ViewModel(){
    lateinit var registerUser : MutableLiveData<ResponseRegisterUser?>

    init {
        registerUser = MutableLiveData()
    }

    fun postLiveDataRegisterUser() : MutableLiveData<ResponseRegisterUser?>{
        return registerUser
    }

    fun postApi(name : RequestBody, email : RequestBody, password : RequestBody, phone : RequestBody, address : RequestBody, city : RequestBody, image : MultipartBody.Part){
        RetrofitClient.instance.registerUser(name, email, password, phone, address, image, city)
            .enqueue(object : retrofit2.Callback<ResponseRegisterUser>{
                override fun onResponse(
                    call: retrofit2.Call<ResponseRegisterUser>,
                    response: retrofit2.Response<ResponseRegisterUser>
                ) {
                    if (response.isSuccessful){
                        registerUser.postValue(response.body())
                    }else{
                        registerUser.postValue(null)
                    }
                }

                override fun onFailure(call: retrofit2.Call<ResponseRegisterUser>, t: Throwable) {
                    registerUser.postValue(null)
                }

            })
    }
}