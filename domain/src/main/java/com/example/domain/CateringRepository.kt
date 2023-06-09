package com.example.domain

import com.example.domain.model.Category
import com.example.domain.model.Dish

interface CateringRepository {
    suspend fun getCategories(): List<Category>
    suspend fun getDishes(): List<Dish>
}