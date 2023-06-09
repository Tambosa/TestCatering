package com.example.testcatering.ui.home

import com.example.domain.model.HomeDisplayableItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class MainAdapter(onCategoryItemClick: (position: Int) -> Unit) :
    ListDelegationAdapter<List<HomeDisplayableItem>>(
        categoryAdapterDelegate(onCategoryItemClick),
    )