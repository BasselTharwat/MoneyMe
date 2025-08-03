package com.example.moneyme.utils

object SmsParser {
    fun parseAmount(message: String): Double? {
        val regex = Regex("(?i)\\b(?:EGP|LE|\\$)?\\s?([0-9]+(?:\\.[0-9]{1,2})?)")
        return regex.find(message)?.groupValues?.get(1)?.toDoubleOrNull()
    }

    fun isFromTrustedSender(sender: String): Boolean {
        return sender.contains("Vodafone") // or load from config
    }
}