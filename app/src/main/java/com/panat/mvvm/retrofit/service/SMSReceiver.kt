package com.panat.mvvm.retrofit.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import com.panat.mvvm.retrofit.model.RequestSms


class SMSReceiver : BroadcastReceiver() {
    companion object {
        private val TAG by lazy { SMSReceiver::class.java.name }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val pdus = intent?.extras?.get("pdus") as Array<*>?
        if (pdus != null) {
            val messages = arrayOfNulls<SmsMessage>(pdus.size)

            var from = ""
            var code = ""
            var text = ""

            messages.forEachIndexed { idx, item ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val format: String = intent?.extras?.getString("format").orEmpty()
                    messages[idx] = SmsMessage.createFromPdu(
                        pdus[idx] as ByteArray?,
                        format
                    )
                } else {
                    messages[idx] =
                        SmsMessage.createFromPdu(pdus[idx] as ByteArray?)
                }

                from = messages[idx]?.originatingAddress.orEmpty()
                code =
                    "${messages[idx]?.originatingAddress.orEmpty()}_${messages[idx]?.timestampMillis ?: 0}"
                val msg = messages[idx]?.messageBody.orEmpty().replace("\n","")
                if (msg.isNotBlank()) {
                    text = "${text}${msg}"
                }
            }

            val result = RequestSms().apply {
                value.set.phone = from
                value.set.text = text.replaceFirst("\n","")
                index.code = code
            }

            context?.let {
                val serviceIntent = UpdateSmsService.createService(it, result)
                it.startService(serviceIntent)
            }
        }

    }
}