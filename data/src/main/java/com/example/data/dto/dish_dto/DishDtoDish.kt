package com.example.data.dto.dish_dto

import com.example.domain.model.Dish
import com.google.gson.annotations.SerializedName

data class DishDtoDish(
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: Int,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Int,
    @SerializedName("tegs") val tags: List<String>,
    @SerializedName("weight") val weight: Int,
)

fun DishDtoDish.toDish() = Dish(
    description = this.description,
    id = this.id,
    imageUrl = this.imageUrl,
    name = this.name,
    price = this.price,
    tags = this.tags,
    weight = this.weight,
)