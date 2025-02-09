package com.example.frontend.data.repository.photo

import com.example.frontend.data.api.MeetMyBarAPI
import com.example.frontend.data.utils.Resource
import com.example.frontend.domain.model.PhotoModel
import com.example.frontend.domain.repository.PhotoRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class PhotoRepository(
    private val meetMyBarAPI: MeetMyBarAPI,
) : PhotoRepositoryInterface, KoinComponent {

    override suspend fun getPhotoById(id: Int): Flow<Resource<PhotoModel?>> = flow {
        emit(Resource.Loading())
        val photoModel = meetMyBarAPI.getPhotoById(id).toModel()
        emit(Resource.Success(photoModel))
    }
}