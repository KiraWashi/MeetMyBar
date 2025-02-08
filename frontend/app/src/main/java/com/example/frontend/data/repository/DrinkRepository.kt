package com.example.frontend.data.repository

import android.icu.text.IDNA
import com.example.frontend.data.utils.Resource
import com.example.frontend.data.utils.Resource.*
import com.example.frontend.data.api.MeetMyBarAPI
import com.example.frontend.domain.model.DrinkModel
import com.example.frontend.domain.repository.DrinkRepositoryInterface
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import kotlinx.coroutines.flow.flow

class DrinkRepository(
    private val meetMyBarAPI: MeetMyBarAPI,
): DrinkRepositoryInterface, KoinComponent {
    override suspend fun getDrinks(): Flow<Resource<List<DrinkModel>?>> = flow {
        emit(Resource.Loading())
        var drinksModel = meetMyBarAPI.getDrinks().map { drinkVo ->
            drinkVo.toModel()
        }
        emit(Success(drinksModel))
    }

    override suspend fun createDrink(drink: DrinkModel): Flow<Resource<DrinkModel?>> = flow {
        emit(Resource.Loading())
        try {
            val drinkVo = drink.toVo()
            val createdDrink = meetMyBarAPI.createDrink(drinkVo).toModel()
            emit(Resource.Success(createdDrink))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override suspend fun updateDrink(id: Int, drink: DrinkModel): Flow<Resource<DrinkModel?>> = flow {
        emit(Resource.Loading())
        try {
            val drinkVo = drink.toVo()
            val updatedDrink = meetMyBarAPI.updateDrink(id, drinkVo).toModel()
            emit(Resource.Success(updatedDrink))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun deleteDrink(id: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            meetMyBarAPI.deleteDrink(id)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }
}