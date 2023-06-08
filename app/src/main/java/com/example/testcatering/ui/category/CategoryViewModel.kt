package com.example.testcatering.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.CateringRepository
import com.example.domain.model.Dish
import kotlinx.coroutines.launch

class CategoryViewModel(private val repo: CateringRepository) : ViewModel() {

    private val _data = MutableLiveData<List<Dish>>(listOf())
    val data: LiveData<List<Dish>> = _data

    private val _selectedTag = MutableLiveData<String>()
    val selectedTag: LiveData<String> = _selectedTag

    fun getDishes() {
        viewModelScope.launch {
            _data.value = repo.getDishes()
        }
    }

    fun selectTag(tag: String) {
        _selectedTag.value = tag
        viewModelScope.launch {
            _data.value = repo.getDishes().filter { it.tags.contains(tag) }
        }
    }
}