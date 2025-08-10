package com.example.moneyme.domain.usecase

import com.example.moneyme.domain.model.Category
import com.example.moneyme.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetAllCategoriesUseCase(
    private val repository: CategoryRepository
) {
    operator fun invoke(): Flow<List<Category>> = repository.getAllCategories()
}

class AddCategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
        // Prevent duplicate names
        val exists = repository.getAllCategoriesOnce().any { it.name.equals(category.name, ignoreCase = true) }
        if (!exists) repository.insertCategory(category)
    }
}

class DeleteCategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Long) {
        val defaultCategory = repository.getDefaultCategory()
        if (categoryId != defaultCategory.categoryId) {
            repository.deleteCategoryById(categoryId)
        }
    }
}

class UpdateCategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
        repository.updateCategory(category)
    }
}

class GetDefaultCategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): Category = repository.getDefaultCategory()
}
