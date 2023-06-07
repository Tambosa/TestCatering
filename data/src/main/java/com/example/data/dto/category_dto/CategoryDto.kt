package com.example.data.dto.category_dto

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("сategories") val categories: List<CategoryDtoCategory>
)