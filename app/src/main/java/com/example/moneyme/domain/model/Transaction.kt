package com.example.moneyme.domain.model

import org.threeten.bp.LocalDateTime


data class Transaction(
    val transactionId: Long = 0L,
    val dateAndTime: LocalDateTime,
    val amount: Double,
    val type: TransactionType, // IN or OUT
    val categoryId: Long? = null,
    val note: String? = null
)

enum class TransactionType { IN, OUT }
