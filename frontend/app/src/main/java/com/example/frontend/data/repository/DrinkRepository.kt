package com.example.frontend.data.repository

import android.util.Log
import com.example.frontend.data.utils.Resource
import com.example.frontend.data.utils.Resource.*
import com.example.frontend.data.api.MeetMyBarAPI
import com.example.frontend.domain.model.DrinkTypeModel
import com.example.frontend.domain.repository.DrinkRepositoryInterface
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import kotlinx.coroutines.flow.flow

class DrinkRepository(
    private val meetMyBarAPI: MeetMyBarAPI,
): DrinkRepositoryInterface, KoinComponent {
    override suspend fun getDrinks(): Flow<Resource<List<DrinkTypeModel>?>> = flow {
        try{
            emit(Loading())
            var drinksModel = meetMyBarAPI.getDrinks().map { drinkVo ->
                drinkVo.toModel() }
            emit(Success(drinksModel))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }
    override suspend fun getDrink(id : Int):  Flow<Resource<DrinkTypeModel?>> = flow {
       try{
           emit(Loading())
            var drinkModel = meetMyBarAPI.getDrink(id).toModel()
            emit(Success(drinkModel))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override suspend fun createDrink(drink: DrinkTypeModel): Flow<Resource<DrinkTypeModel?>> = flow {
        emit(Loading())
        try {
            val drinkVo = drink.toVo()
            val createdDrink = meetMyBarAPI.createDrink(drinkVo).toModel()
            emit(Success(createdDrink))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override suspend fun updateDrink(drink: DrinkTypeModel): Flow<Resource<DrinkTypeModel?>> = flow {
        emit(Loading())
        try {
            val drinkVo = drink.toVo()
            val updatedDrink = meetMyBarAPI.updateDrink(drinkVo).toModel()
            emit(Success(updatedDrink))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override suspend fun deleteDrink(id: Int): Flow<Resource<Unit>> = flow {
        emit(Loading())
        try {
            meetMyBarAPI.deleteDrink(id)
            emit(Success(Unit))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override suspend fun addDrinkToBar(
        idBar: Int,
        idDrink: Int,
        volume: String,
        price: String
    ): Flow<Resource<Unit>> = flow {
        emit(Loading())
        try {
            val httpResponse = meetMyBarAPI.addDrinkToBar(
                idBar = idBar,
                idDrink = idDrink,
                volume = volume,
                price = price
            )
            emit(Success(Unit))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override suspend fun deleteBarDrinkLink(
        idBar: Int,
        idDrink: Int,
        volume: Int
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = meetMyBarAPI.deleteBarDrinkLink(
                idBar = idBar,
                idDrink = idDrink,
                volume = volume
            )
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            Log.e("DrinkBarLinkRepository", "Erreur lors de la suppression du lien: ${e.message}")
            emit(Resource.Error(e))
        }
    }
}
