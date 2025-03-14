package com.example.frontend.domain.repository

import com.example.frontend.data.repository.photo.BarPhoto
import com.example.frontend.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PhotoRepositoryInterface {
    suspend fun getPhotoById(id: Int): Flow<Resource<ByteArray>>
    suspend fun getPhotosByBar(barId: Int): List<BarPhoto>
}