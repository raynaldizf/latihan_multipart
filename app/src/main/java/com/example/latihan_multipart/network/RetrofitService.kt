package com.example.latihan_multipart.network

import com.example.latihan_multipart.model.ResponseRegisterUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitService {

    @POST("auth/register")
    @Multipart
    fun registerUser(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("address") address: RequestBody,
        @Part fileImage : MultipartBody.Part,
        @Part("city") city: RequestBody,
    ) : Call<ResponseRegisterUser>
}