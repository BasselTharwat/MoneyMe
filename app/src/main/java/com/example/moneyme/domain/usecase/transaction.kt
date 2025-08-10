package com.example.moneyme.domain.usecase

import com.example.moneyme.domain.model.Transaction
import com.example.moneyme.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class AddTransactionUseCase(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        repository.insertTransaction(transaction)
    }
}

class DeleteTransactionUseCase(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transactionId: Long) {
        repository.deleteTransactionById(transactionId)
    }
}

class UpdateTransactionUseCase(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        repository.updateTransaction(transaction)
    }
}

class GetTransactionsByCategoryUseCase(
    private val repository: TransactionRepository
) {
    operator fun invoke(categoryId: Long): Flow<List<Transaction>> {
        return repository.getTransactionsByCategory(categoryId)
    }
}

class GetAllTransactionsUseCase(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<List<Transaction>> = repository.getAllTransactions()
}
