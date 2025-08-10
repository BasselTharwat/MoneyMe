package com.example.moneyme.di

import com.example.moneyme.data.local.dao.CategoryDao
import com.example.moneyme.data.local.dao.TransactionDao
import com.example.moneyme.data.repository.CategoryRepositoryImpl
import com.example.moneyme.data.repository.TransactionRepositoryImpl
import com.example.moneyme.domain.repository.CategoryRepository
import com.example.moneyme.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideTransactionRepository(
        dao: TransactionDao
    ): TransactionRepository = TransactionRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideCategoryRepository(
        dao: CategoryDao
    ): CategoryRepository = CategoryRepositoryImpl(dao)
}
