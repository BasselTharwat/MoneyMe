package com.example.moneyme.domain.repository

import com.example.moneyme.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(): Flow<List<Category>>
    suspend fun getAllCategoriesOnce(): List<Category>
    suspend fun insertCategory(category: Category)
    suspend fun deleteCategoryById(categoryId: Long)
    suspend fun updateCategory(category: Category)
    suspend fun getDefaultCategory(): Category
}

