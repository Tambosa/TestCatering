package com.example.data

import com.example.domain.CartRepository
import com.example.domain.model.Dish

class CartRepositoryFakeImpl : CartRepository {
    private val cartList: MutableMap<Dish, Int> = mutableMapOf()
    override suspend fun addToCart(dish: Dish) {
        if (cartList[dish] != null) {
            cartList[dish] = cartList[dish]!! + 1
        } else {
            cartList[dish] = 1
        }
    }

    override suspend fun removeFromCart(dish: Dish) {
        if (cartList[dish] != null) {
            if (cartList[dish] == 1) {
                cartList.remove(dish)
            } else if (cartList[dish]!! > 1) {
                cartList[dish] = cartList[dish]!! - 1
            }
        }
    }

    override suspend fun getCart(): List<Pair<Dish, Int>> {
        return cartList.map { Pair(it.key, it.value) }
    }
}