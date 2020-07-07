package com.panat.mvvm.retrofit.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import com.panat.mvvm.retrofit.model.RequestSms


class SMSReceiver : BroadcastReceiver() {
    companion object {
        private val TAG by lazy { SMSReceiver::class.java.name }
    }

    private val displayMessageBody: MutableLiveData<RequestSms> = MutableLiveData<RequestSms>()
    fun getDisplayMessageBody(): LiveData<RequestSms>? {
        return displayMessageBody
    }

    private val obj = RequestSms()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!intent?.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) return
        val extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        extractMessages.forEach { smsMessage ->
            Log.v(TAG, smsMessage.displayMessageBody + " " + smsMessage.displayOriginatingAddress)
            obj.value?.text = smsMessage.displayMessageBody
            obj.value?.phone = smsMessage.displayOriginatingAddress
            displayMessageBody.postValue(obj)
        }

    }
}