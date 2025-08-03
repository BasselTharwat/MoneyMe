package com.example.moneyme.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.extras
        val pdus = bundle?.get("pdus") as? Array<*>
        val messages = pdus?.mapNotNull { pdu ->
            SmsMessage.createFromPdu(pdu as ByteArray, bundle.getString("format"))
        }

        messages?.forEach { message ->
            val sender = message.originatingAddress
            val content = message.messageBody
            Log.d("SmsReceiver", "SMS from $sender: $content")

        }
    }

}