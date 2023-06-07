package com.example.data.dto.category_dto

import com.example.domain.model.Category
import com.google.gson.annotations.SerializedName

data class CategoryDtoCategory(
    @SerializedName("id") val id: Int,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("name") val name: String,
)

fun CategoryDtoCategory.toCategory() = Category(
    id = this.id,
    imageUrl = this.imageUrl,
    name = this.name,
)