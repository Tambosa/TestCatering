package com.example.data

import com.example.data.dto.category_dto.toCategory
import com.example.data.dto.dish_dto.toDish
import com.example.domain.CateringRepository

class CateringRepositoryImpl(private val api: CateringApi) : CateringRepository {
    override suspend fun getCategories() = api.getCategories().categories.map { it.toCategory() }

    override suspend fun getDishes() = api.getDishes().dishes.map { it.toDish() }
}