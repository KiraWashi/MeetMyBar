package com.example.frontend.domain.repository

import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.model.DrinkModel
import kotlinx.coroutines.flow.Flow

interface DrinkRepositoryInterface {
    suspend fun getDrinks(): Flow<Resource<List<DrinkModel>?>>
}