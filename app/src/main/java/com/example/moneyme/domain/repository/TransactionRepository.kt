package com.example.moneyme.domain.repository

import com.example.moneyme.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getAllTransactions(): Flow<List<Transaction>>
    fun getTransactionsByCategory(categoryId: Long): Flow<List<Transaction>>
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun deleteTransactionById(transactionId: Long)
    suspend fun updateTransaction(transaction: Transaction)
}

