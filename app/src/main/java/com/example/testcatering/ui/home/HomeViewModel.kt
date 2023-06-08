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

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading
    fun getCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            _data.value = repo.getCategories()
            _isLoading.value = false
        }
    }
}