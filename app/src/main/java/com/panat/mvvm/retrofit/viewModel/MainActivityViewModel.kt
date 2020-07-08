package com.panat.mvvm.retrofit.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.panat.mvvm.retrofit.model.RequestSms
import com.panat.mvvm.retrofit.model.SendSmsResult
import com.panat.mvvm.retrofit.model.smsList
import com.panat.mvvm.retrofit.service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivityViewModel(private val retrofit: ApiService) : ViewModel() {

    private val _smsList = MutableLiveData<smsList>()
    val sms: LiveData<smsList>
        get() = _smsList

    fun loadSmsList() {
        retrofit.getSmsList().enqueue(object : Callback<smsList> {
            override fun onResponse(
                call: Call<smsList>,
                response: Response<smsList>
            ) {
                _smsList.postValue(response.body())
            }

            override fun onFailure(call: Call<smsList>, t: Throwable) {
                println("onFailure ${t.message}")
            }
        })
    }

    /*private val _smsSendResult = MutableLiveData<SendSmsResult>()
    val smsSendResult: LiveData<SendSmsResult>
        get() = _smsSendResult

    fun sendSMS(data: RequestSms) {
        retrofit.sendSMS(data).enqueue(object : Callback<SendSmsResult> {
            override fun onResponse(
                call: Call<SendSmsResult>,
                response: Response<SendSmsResult>
            ) {
                _smsSendResult.postValue(response.body())
            }

            override fun onFailure(call: Call<SendSmsResult>, t: Throwable) {
                println("onFailure ${t.message}")
            }
        })
    }*/


}