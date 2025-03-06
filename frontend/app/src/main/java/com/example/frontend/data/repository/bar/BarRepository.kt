package com.example.frontend.data.repository.bar

import android.util.Log
import com.example.frontend.data.api.MeetMyBarAPI
import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.model.BarModel
import com.example.frontend.domain.model.SimpleBarModel
import com.example.frontend.domain.repository.BarRepositoryInterface
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BarRepository(
    private val meetMyBarAPI: MeetMyBarAPI
) : BarRepositoryInterface {
    override suspend fun getBars(): Flow<Resource<List<BarModel>?>> = flow {
        emit(Resource.Loading())
        try {
            val barsModel = meetMyBarAPI.getBars().map { barVo ->
                barVo.toModel()
            }
            emit(Resource.Success(barsModel))
        } catch(e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun addBars(bar: SimpleBarModel): Flow<Resource<HttpResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = meetMyBarAPI.postBar(bar = bar.toVo())
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e))
            Log.e("Erreur Appel Api", e.toString())
        }
    }

    override suspend fun getBarsById(barId: Int): Flow<Resource<BarModel>> = flow {
        emit(Resource.Loading())
        try {
            val barModel = meetMyBarAPI.getBarById(barId).toModel()
            emit(Resource.Success(barModel))
        } catch(e: Exception) {
            emit(Resource.Error(e))
        }
    }
}