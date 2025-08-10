package com.example.moneyme.di

import android.app.Application
import androidx.room.Room
import com.example.moneyme.data.local.MoneyMeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): MoneyMeDatabase =
        Room.databaseBuilder(app, MoneyMeDatabase::class.java, "transactions.db").build()

    @Provides
    fun provideCategoryDao(db: MoneyMeDatabase) = db.categoryDao()

    @Provides
    fun provideTransactionDao(db: MoneyMeDatabase) = db.transactionDao()
}
