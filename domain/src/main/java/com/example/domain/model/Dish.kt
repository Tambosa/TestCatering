package com.example.domain.model

data class Dish(
    val description: String,
    val id: Int,
    val imageUrl: String?,
    val name: String,
    val price: Int,
    val tags: List<String>,
    val weight: Int,
): DishDisplayableItem
