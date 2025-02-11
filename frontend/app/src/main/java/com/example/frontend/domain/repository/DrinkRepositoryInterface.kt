package com.example.frontend.domain.repository

import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.model.DrinkModel
import kotlinx.coroutines.flow.Flow

interface DrinkRepositoryInterface {
    suspend fun getDrinks(): Flow<Resource<List<DrinkModel>?>>
    suspend fun getDrink(id: Int):  Flow<Resource<DrinkModel?>>
    suspend fun createDrink(drink: DrinkModel): Flow<Resource<DrinkModel?>>
    suspend fun updateDrink(drink: DrinkModel): Flow<Resource<DrinkModel?>>
    suspend fun deleteDrink(id: Int): Flow<Resource<Unit>>
}