package com.panat.mvvm.retrofit.view

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.panat.mvvm.retrofit.R
import com.panat.mvvm.retrofit.adapter.SmsListAdapter
import com.panat.mvvm.retrofit.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        private const val REQUEST_CODE_SMS_PERMISSION = 1
    }

    private lateinit var smsUpdateReceiver: SmsUpdateReceiver

    private val viewModel: MainActivityViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.message_list)

        val adapter = SmsListAdapter(this)
        rvEvent.layoutManager = LinearLayoutManager(this)
        rvEvent.adapter = adapter

        viewModel.sms.observe(this, androidx.lifecycle.Observer {
            adapter.loadData(it)
        })
        viewModel.loadSmsList()

        requestSmsPermission()
        /*val intentFilter = IntentFilter()
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED")
        registerReceiver(smsReceiver, intentFilter)

        smsReceiver.getDisplayMessageBody()?.observe(this, Observer {
            println(it)
            viewModel.sendSMS(it)
        })

        viewModel.smsSendResult.observe(this, Observer {
            viewModel.loadSmsList()
        })*/

        registerSmsUpdateReceiver()

    }

    private fun registerSmsUpdateReceiver() {
        val filter = IntentFilter("$packageName.NEW_MESSAGE")
        smsUpdateReceiver = SmsUpdateReceiver()
        registerReceiver(smsUpdateReceiver, filter)
    }

    private fun requestSmsPermission() {
        val receiveSmsPermission = Manifest.permission.RECEIVE_SMS
        val readSmsPermission = Manifest.permission.READ_SMS
        val isReceiveGrant = ContextCompat.checkSelfPermission(this, receiveSmsPermission)
        val isReadGrant = ContextCompat.checkSelfPermission(this, readSmsPermission)
        if (isReceiveGrant != PackageManager.PERMISSION_GRANTED && isReadGrant != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(receiveSmsPermission, readSmsPermission),
                REQUEST_CODE_SMS_PERMISSION
            )
        }
    }

    inner class SmsUpdateReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.loadSmsList()
        }

    }

    override fun onDestroy() {
        if (this::smsUpdateReceiver.isInitialized)
            unregisterReceiver(smsUpdateReceiver)
        super.onDestroy()
    }
}

