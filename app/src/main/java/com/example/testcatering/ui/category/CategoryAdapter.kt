package com.example.testcatering.ui.category

import com.example.domain.model.DishDisplayableItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class CategoryAdapter(onDishItemClick: (position: Int) -> Unit) :
    ListDelegationAdapter<List<DishDisplayableItem>>(
        dishAdapterDelegate(onDishItemClick),
    )