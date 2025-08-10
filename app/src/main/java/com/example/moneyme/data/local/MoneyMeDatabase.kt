package com.example.moneyme.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneyme.data.local.dao.CategoryDao
import com.example.moneyme.data.local.dao.TransactionDao
import com.example.moneyme.data.local.entity.CategoryEntity
import com.example.moneyme.data.local.entity.TransactionEntity

@Database(
    entities = [CategoryEntity::class, TransactionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MoneyMeDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
}
