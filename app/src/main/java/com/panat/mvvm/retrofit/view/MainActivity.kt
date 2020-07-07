package com.panat.mvvm.retrofit.view

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.panat.mvvm.retrofit.R
import com.panat.mvvm.retrofit.adapter.SmsListAdapter
import com.panat.mvvm.retrofit.service.SMSReceiver
import com.panat.mvvm.retrofit.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        private const val REQUEST_CODE_SMS_PERMISSION = 1
    }

    private val smsReceiver by lazy { SMSReceiver() }

    private val viewModel: MainActivityViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.message_list)

        val adapter = SmsListAdapter(this)
        rvEvent.layoutManager = LinearLayoutManager(this)
        rvEvent.adapter = adapter

        viewModel.loadSmsList()
        viewModel.sms.observe(this, androidx.lifecycle.Observer {
            adapter.loadData(it)
        })

        requestSmsPermission()
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED")
        registerReceiver(smsReceiver, intentFilter)

        smsReceiver.getDisplayMessageBody()?.observe(this, Observer {
            println(it)
            viewModel.sendSMS(it)
        })

        viewModel.smsSendResult.observe(this, Observer {
            viewModel.loadSmsList()
        })
    }

    private fun requestSmsPermission() {
        val permission = Manifest.permission.RECEIVE_SMS
        val grant = ContextCompat.checkSelfPermission(this, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                REQUEST_CODE_SMS_PERMISSION
            )
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(smsReceiver)
    }
}

