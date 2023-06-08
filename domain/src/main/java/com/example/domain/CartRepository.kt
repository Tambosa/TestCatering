package com.example.domain

import com.example.domain.model.Dish

interface CartRepository {
    suspend fun addToCart(dish: Dish)
    suspend fun removeFromCart(dish: Dish)
    suspend fun getCart(): List<Pair<Dish, Int>>
}