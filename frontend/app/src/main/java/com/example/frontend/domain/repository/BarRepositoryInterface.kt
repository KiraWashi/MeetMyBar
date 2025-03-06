package com.example.frontend.domain.repository

import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.model.BarModel
import com.example.frontend.domain.model.SimpleBarModel
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow

interface BarRepositoryInterface {
    suspend fun getBars(): Flow<Resource<List<BarModel>?>>
    suspend fun addBars(bar: SimpleBarModel): Flow<Resource<HttpResponse>>
    suspend fun getBarsById(barId: Int): Flow<Resource<BarModel>>
}