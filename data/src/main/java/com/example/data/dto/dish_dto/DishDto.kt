package com.example.data.dto.dish_dto

import com.google.gson.annotations.SerializedName

data class DishDto(
    @SerializedName("dishes") val dishes: List<DishDtoDish>
)