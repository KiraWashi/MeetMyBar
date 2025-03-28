package com.example.frontend.domain.repository

import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.model.DrinkTypeModel
import kotlinx.coroutines.flow.Flow

interface DrinkRepositoryInterface {
    suspend fun getDrinks(): Flow<Resource<List<DrinkTypeModel>?>>
    suspend fun getDrink(id: Int):  Flow<Resource<DrinkTypeModel?>>
    suspend fun createDrink(drink: DrinkTypeModel): Flow<Resource<DrinkTypeModel?>>
    suspend fun updateDrink(drink: DrinkTypeModel): Flow<Resource<DrinkTypeModel?>>
    suspend fun deleteDrink(id: Int): Flow<Resource<Unit>>
    suspend fun deleteBarDrinkLink(idBar: Int, idDrink: Int, volume: Double): Flow<Resource<Unit>>
    suspend fun addDrinkToBar(idBar: Int, idDrink: Int, volume: Double, price: Double): Flow<Resource<Unit>>
    suspend fun updateDrinkPrice(idBar: Int, idDrink: Int, volume: Float, newPrice: Float): Flow<Resource<Unit>>
}