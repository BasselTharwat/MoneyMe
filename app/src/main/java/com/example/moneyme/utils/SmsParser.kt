package com.example.moneyme.utils

import android.util.Log

object SmsParser {
    fun parseAmount(message: String): Double? {
        val regex = Regex("(?i)EGP\\s+([0-9]+(?:\\.[0-9]{1,2})?)")
        return regex.find(message)?.groupValues?.get(1)?.toDoubleOrNull()
    }

    fun isFromTrustedSender(sender: String): Boolean {
        return sender.contains("EmiratesNBD")
    }
}