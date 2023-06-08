package com.example.testcatering.ui.cart

import com.example.domain.model.Dish
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class CartAdapter(
    onPlus: (position: Int) -> Unit,
    onMinus: (position: Int) -> Unit
) :
    ListDelegationAdapter<List<Pair<Dish, Int>>>(
        cartAdapterDelegate(onPlus, onMinus),
    )