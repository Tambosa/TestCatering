package com.example.testcatering.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.CartRepository
import com.example.domain.model.Dish
import kotlinx.coroutines.launch

class CartViewModel(private val cartRepo: CartRepository) : ViewModel() {

    private val _cart = MutableLiveData<List<Pair<Dish, Int>>>(listOf())
    val cart: LiveData<List<Pair<Dish, Int>>> = _cart

    fun getCart() {
        viewModelScope.launch {
            _cart.value = cartRepo.getCart()
        }
    }

    fun addItem(dish: Dish) {
        viewModelScope.launch {
            cartRepo.addToCart(dish)
            getCart()
        }
    }

    fun removeItem(dish: Dish) {
        viewModelScope.launch {
            cartRepo.removeFromCart(dish)
            getCart()
        }
    }
}