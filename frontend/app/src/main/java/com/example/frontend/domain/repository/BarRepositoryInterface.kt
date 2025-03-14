package com.example.frontend.domain.repository

import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.model.BarModel
import com.example.frontend.domain.model.BasicBarModel
import com.example.frontend.domain.model.SimpleBarModel
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow

interface BarRepositoryInterface {
    suspend fun getBars(): Flow<Resource<List<BarModel>?>>
    suspend fun addBars(bar: SimpleBarModel): Flow<Resource<HttpResponse>>
    suspend fun getBarById(barId: Int): Flow<Resource<BarModel>>
    suspend fun deleteBarById(barId: Int): Flow<Resource<HttpResponse>>
    suspend fun modifyBar(bar: BasicBarModel): Flow<Resource<HttpResponse>>
}