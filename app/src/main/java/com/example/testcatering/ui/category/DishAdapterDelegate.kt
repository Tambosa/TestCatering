package com.example.testcatering.ui.category

import coil.load
import com.example.domain.model.Dish
import com.example.domain.model.DishDisplayableItem
import com.example.testcatering.R
import com.example.testcatering.databinding.ItemDishBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun dishAdapterDelegate(onItemClick: (position: Int) -> Unit) =
    adapterDelegateViewBinding<Dish, DishDisplayableItem, ItemDishBinding>({ layoutInflater, root ->
        ItemDishBinding.inflate(layoutInflater, root, false)
    }) {
        binding.root.setOnClickListener { onItemClick(layoutPosition) }
        bind {
            binding.dishName.text = item.name
            item.imageUrl?.let {
                binding.dishImage.load(it)
            } ?: binding.dishImage.load(R.drawable.placeholder)
        }
    }