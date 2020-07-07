package com.panat.mvvm.retrofit.service

import com.panat.mvvm.retrofit.model.RequestSms
import com.panat.mvvm.retrofit.model.SendSmsResult
import com.panat.mvvm.retrofit.model.smsList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("sms/fetchFind")
    fun getSmsList(): Call<smsList>

    @POST("api/fetchUpdateOne")
    fun sendSMS(@Body data: RequestSms): Call<SendSmsResult>
}

