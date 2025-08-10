package com.example.moneyme.data.mapper

import com.example.moneyme.data.local.entity.TransactionEntity
import com.example.moneyme.domain.model.Transaction
import com.example.moneyme.domain.model.TransactionType
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter


private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        transactionId = this.transactionId.toLong(),
        dateAndTime = LocalDateTime.parse(this.dateAndTime, formatter),
        amount = this.amount,
        type = if (this.type.lowercase() == "in") TransactionType.IN else TransactionType.OUT,
        categoryId = this.categoryId?.toLong(),
        note = this.note
    )
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        transactionId = this.transactionId.toInt(),
        dateAndTime = this.dateAndTime.format(formatter),
        amount = this.amount,
        type = if (this.type == TransactionType.IN) "in" else "out",
        categoryId = this.categoryId?.toInt(),
        note = this.note ?: ""
    )
}
