package com.example.moneyme.domain.model

data class Category(
    val categoryId: Long = 0L,
    val name: String,
    val target: Double,
    val current: Double
)
