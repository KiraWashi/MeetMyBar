package com.example.frontend.data.repository

import com.example.frontend.data.utils.Resource
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
        emit(Resource.Success(drinksModel))
    }
}