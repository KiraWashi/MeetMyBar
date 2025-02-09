package com.example.frontend.domain.repository

import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.model.PhotoModel
import kotlinx.coroutines.flow.Flow

interface PhotoRepositoryInterface {
    suspend fun getPhotoById(id: Int): Flow<Resource<PhotoModel?>>
}