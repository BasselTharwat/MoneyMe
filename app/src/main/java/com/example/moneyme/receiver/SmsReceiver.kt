package com.example.moneyme.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import com.example.moneyme.notification.SmsNotificationManager
import com.example.moneyme.utils.SmsParser

data class SmsData(val sender: String, val body: String)

fun extractSms(intent: Intent): SmsData {
    val bundle = intent.extras ?: return SmsData("", "")
    val pdus = bundle["pdus"] as? Array<*> ?: return SmsData("", "")
    val format = bundle.getString("format")

    val messages = pdus.mapNotNull {
        SmsMessage.createFromPdu(it as ByteArray, format)
    }

    val fullMessage = messages.joinToString(separator = "") { it.messageBody }
    val sender = messages.firstOrNull()?.originatingAddress ?: ""

    return SmsData(sender, fullMessage)
}


class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val sms = extractSms(intent)
        val amount = SmsParser.parseAmount(sms.body)
        if (SmsParser.isFromTrustedSender(sms.sender) && amount != null) {
            SmsNotificationManager.showTransactionPrompt(context, sms.sender, amount, sms.body)
        }
    }


}