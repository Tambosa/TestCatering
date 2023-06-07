package com.example.testcatering.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.CateringRepository
import com.example.domain.model.Category
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: CateringRepository) : ViewModel() {

    private val _data = MutableLiveData<List<Category>>(listOf())
    val data: LiveData<List<Category>> = _data

    fun getCategories() {
        viewModelScope.launch {
            _data.value = repo.getCategories()
        }
    }
}