package com.example.testcatering.ui.cart

import coil.load
import com.example.domain.model.Dish
import com.example.testcatering.databinding.ItemCartBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun cartAdapterDelegate(
    onPlus: (position: Int) -> Unit,
    onMinus: (position: Int) -> Unit
) =
    adapterDelegateViewBinding<Pair<Dish, Int>, Pair<Dish, Int>, ItemCartBinding>({ layoutInflater, root ->
        ItemCartBinding.inflate(layoutInflater, root, false)
    }) {
        bind {
            with(binding) {
                dishQnt.text = item.second.toString()
                dishName.text = item.first.name
                dishPrice.text = "${item.first.price} ₽"
                dishWeight.text = " · ${item.first.weight} г"
                imageDish.load(item.first.imageUrl)
                btnMinus.setOnClickListener { onMinus(layoutPosition) }
                btnPlus.setOnClickListener { onPlus(layoutPosition) }
            }
        }
    }