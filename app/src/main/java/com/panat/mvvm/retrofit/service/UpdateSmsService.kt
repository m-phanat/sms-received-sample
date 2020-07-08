package com.panat.mvvm.retrofit.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.panat.mvvm.retrofit.model.RequestSms
import com.panat.mvvm.retrofit.model.SendSmsResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class UpdateSmsService: Service() {

    companion object{
        private const val EXTRA_REQUEST="sms_request"

        @JvmStatic
        fun createService(context: Context,requestSms: RequestSms):Intent{
            return Intent(context,UpdateSmsService::class.java).apply {
                putExtra(EXTRA_REQUEST,requestSms)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getParcelableExtra<RequestSms?>(EXTRA_REQUEST)?.let {
            updateSms(it)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun updateSms(data: RequestSms) {
        val retrofit= Retrofit.Builder()
            .baseUrl("http://18.140.130.253:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)

        retrofit.sendSMS(data).enqueue(object : Callback<SendSmsResult> {
            override fun onResponse(
                call: Call<SendSmsResult>,
                response: Response<SendSmsResult>
            ) {
                Log.d("update_sms_api","success")

                val filter= "$packageName.NEW_MESSAGE"
                val intent=Intent().apply {
                    action = filter
                }

                sendBroadcast(intent)
            }

            override fun onFailure(call: Call<SendSmsResult>, t: Throwable) {
                Log.d("update_sms_api",t.message.orEmpty())
            }
        })
    }
}