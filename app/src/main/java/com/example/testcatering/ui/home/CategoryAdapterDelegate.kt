package com.example.testcatering.ui.home

import coil.load
import com.example.domain.model.Category
import com.example.domain.model.HomeDisplayableItem
import com.example.testcatering.databinding.ItemCategoryBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun categoryAdapterDelegate(onItemClick: (position: Int) -> Unit) =
    adapterDelegateViewBinding<Category, HomeDisplayableItem, ItemCategoryBinding>({ layoutInflater, root ->
        ItemCategoryBinding.inflate(layoutInflater, root, false)
    }) {
        binding.root.setOnClickListener { onItemClick(layoutPosition) }
        bind {
            binding.categoryTitle.text = item.name
            binding.categoryImage.load(item.imageUrl)
        }
    }