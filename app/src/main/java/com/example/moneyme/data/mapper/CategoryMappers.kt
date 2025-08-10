package com.example.moneyme.data.mapper
import com.example.moneyme.data.local.entity.CategoryEntity
import com.example.moneyme.domain.model.Category

fun CategoryEntity.toDomain(): Category {
    return Category(
        categoryId = this.categoryId.toLong(),
        name = this.name,
        target = this.target,
        current = this.current
    )
}

fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(
        categoryId = this.categoryId.toInt(),
        name = this.name,
        target = this.target,
        current = this.current
    )
}
