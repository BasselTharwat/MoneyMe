package com.example.moneyme.di

import com.example.moneyme.domain.repository.CategoryRepository
import com.example.moneyme.domain.repository.TransactionRepository
import com.example.moneyme.domain.usecase.AddCategoryUseCase
import com.example.moneyme.domain.usecase.AddTransactionUseCase
import com.example.moneyme.domain.usecase.DeleteCategoryUseCase
import com.example.moneyme.domain.usecase.DeleteTransactionUseCase
import com.example.moneyme.domain.usecase.GetAllCategoriesUseCase
import com.example.moneyme.domain.usecase.GetAllTransactionsUseCase
import com.example.moneyme.domain.usecase.GetDefaultCategoryUseCase
import com.example.moneyme.domain.usecase.GetTransactionsByCategoryUseCase
import com.example.moneyme.domain.usecase.UpdateCategoryUseCase
import com.example.moneyme.domain.usecase.UpdateTransactionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    // --- Category Use Cases ---
    @Provides
    @Singleton
    fun provideCategoryUseCases(
        categoryRepository: CategoryRepository
    ): CategoryUseCases {
        return CategoryUseCases(
            getAllCategories = GetAllCategoriesUseCase(categoryRepository),
            addCategory = AddCategoryUseCase(categoryRepository),
            deleteCategory = DeleteCategoryUseCase(categoryRepository),
            updateCategory = UpdateCategoryUseCase(categoryRepository),
            getDefaultCategory = GetDefaultCategoryUseCase(categoryRepository)
        )
    }

    // --- Transaction Use Cases ---
    @Provides
    @Singleton
    fun provideTransactionUseCases(
        transactionRepository: TransactionRepository
    ): TransactionUseCases {
        return TransactionUseCases(
            addTransaction = AddTransactionUseCase(transactionRepository),
            deleteTransaction = DeleteTransactionUseCase(transactionRepository),
            updateTransaction = UpdateTransactionUseCase(transactionRepository),
            getTransactionsByCategory = GetTransactionsByCategoryUseCase(transactionRepository),
            getAllTransactions = GetAllTransactionsUseCase(transactionRepository)
        )
    }

    // Category
    data class CategoryUseCases(
        val getAllCategories: GetAllCategoriesUseCase,
        val addCategory: AddCategoryUseCase,
        val deleteCategory: DeleteCategoryUseCase,
        val updateCategory: UpdateCategoryUseCase,
        val getDefaultCategory: GetDefaultCategoryUseCase
    )

    // Transaction
    data class TransactionUseCases(
        val addTransaction: AddTransactionUseCase,
        val deleteTransaction: DeleteTransactionUseCase,
        val updateTransaction: UpdateTransactionUseCase,
        val getTransactionsByCategory: GetTransactionsByCategoryUseCase,
        val getAllTransactions: GetAllTransactionsUseCase
    )

}

