package com.example.data

import com.example.data.dto.category_dto.CategoryDto
import com.example.data.dto.dish_dto.DishDto
import retrofit2.http.GET

interface CateringApi {
    @GET("/v3/058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getCategories(): CategoryDto

    @GET("/v3/c7a508f2-a904-498a-8539-09d96785446e")
    suspend fun getDishes(): DishDto
}