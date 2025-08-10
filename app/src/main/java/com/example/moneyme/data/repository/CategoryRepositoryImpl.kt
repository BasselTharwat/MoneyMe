package com.example.moneyme.data.repository

import com.example.moneyme.data.local.dao.CategoryDao
import com.example.moneyme.data.mapper.toDomain
import com.example.moneyme.data.mapper.toEntity
import com.example.moneyme.domain.model.Category
import com.example.moneyme.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(
    private val dao: CategoryDao
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> {
        return dao.getAllCategories().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getAllCategoriesOnce(): List<Category> {
        return dao.getAllCategoriesOnce().map { it.toDomain() }
    }

    override suspend fun insertCategory(category: Category) {
        dao.insertCategory(category.toEntity())
    }

    override suspend fun deleteCategoryById(categoryId: Long) {
        dao.deleteCategoryById(categoryId)
    }

    override suspend fun updateCategory(category: Category) {
        dao.updateCategory(category.toEntity())
    }

    override suspend fun getDefaultCategory(): Category {
        return dao.getDefaultCategory().toDomain()
    }
}
